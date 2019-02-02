package world.gregs.hestia.file.codec.encoders

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.network.codec.packet.PacketWriter

internal class CacheArchiveEncoderTest {

    private var dataLength: Int = -1
    private var dataHeader: Int = -1
    private lateinit var data: ByteArray
    private lateinit var encoded: ByteArray
    private lateinit var expected: ByteArray

    @Test
    fun `Encode plain data`() {
        //Given
        data(10, 0)
        //When
        encode(0, 5)
        //Then
        assertEncoded(-1, 2, 2, 2, 2, -1, 2, 2, 2, 2, -1, 2, 2)
    }

    @Test
    fun `Encode another size`() {
        //Given
        data(12, 0)
        //When
        encode(0, 6)
        //Then
        assertEncoded(-1, 2, 2, 2, 2, 2, -1, 2, 2, 2, 2, 2, -1, 2, 2)
    }

    @Test
    fun `Encode less chunk size than length`() {
        //Given
        data(6, 0)
        //When
        encode(0, 8)
        //Then
        assertEncoded(-1, 2, 2, 2, 2, 2, 2)
    }

    @Test
    fun `Encode with small header`() {
        //Given
        data(10, 2)
        //When
        encode(0, 4)
        //Then
        assertEncoded(-1, 2, 2, 2, -1, 2, 2, 2, -1, 2, 2, 2, -1, 2)
    }

    @Test
    fun `Encode with large header`() {
        //Given
        data(12, 20)
        //When
        encode(0, 2)
        //Then
        assertEncoded(-1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2)
    }

    @Test
    fun `Encode with single offset`() {
        //Given
        data(8, 0)
        //When
        encode(1, 2)
        //Then
        assertEncoded(0, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2)
    }

    @Test
    fun `Encode with offset`() {
        //Given
        data(8, 0)
        //When
        encode(2, 2)
        //Then
        assertEncoded(0, 0, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2)
    }

    @Test
    fun `Encode with large offset`() {
        //Given
        data(8, 0)
        //When
        encode(9, 2)
        //Then
        assertEncoded(0, 0, 0, 0, 0, 0, 0, 0, 0, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2, -1, 2)
    }

    @Test
    fun `Use case lower`() {
        //Given
        data(1525, 5)
        //When
        encode(10, 512)
        //Then
        assert(10, 512)
    }

    @Test
    fun `Use case higher`() {
        //Given
        data(1536, 5)
        //When
        encode(10, 512)
        //Then
        assert(10, 512)
    }

    @Test
    fun `Second use case lower`() {
        //Given
        data(1013, 5)
        //When
        encode(10, 512)
        //Then
        assert(10, 512)
    }
    @Test
    fun `Second use case higher`() {
        //Given
        data(1022, 5)
        //When
        encode(1, 512)
        //Then
        assert(1, 512)
    }

    @Test
    fun `Third use case offset-less`() {
        //Given
        data(1533, 5)
        //When
        encode(0, 512)
        //Then
        assert(0, 512)
    }

    @Test
    fun `Third use case offset`() {
        //Given
        data(1533, 5)
        //When
        encode(2, 512)
        //Then
        assert(2, 512)
    }

    private fun data(dataLength: Int, dataHeader: Int) {
        this.dataLength = dataLength
        this.dataHeader = dataHeader
        val data = arrayOfNulls<Byte>(dataLength + dataHeader)
        data.fill(2)
        this.data = data.filterNotNull().toTypedArray().toByteArray()
    }

    private fun encode(dataOffset: Int, chunkSize: Int) {
        val builder = PacketWriter()
        builder.skip(dataOffset)
        CacheArchiveEncoder.encode(builder, data, dataLength, dataHeader, chunkSize)
        encoded = builder.buffer.array().copyOf(builder.buffer.readableBytes())
        expected(dataOffset, chunkSize)
    }

    private fun assertEncoded(vararg data: Byte) {
        assertThat(this.encoded).containsExactly(*data)
    }

    private fun assert(dataOffset: Int, chunkSize: Int) {
        var position = dataOffset
        for (i in dataHeader until dataLength + dataHeader) {
            if (position % chunkSize == 0) {
                assertThat(encoded[position++]).isEqualTo(-1)
            }
            assertThat(encoded[position++]).isEqualTo(data[i])
        }
        assertThat(this.encoded).containsExactly(*this.expected)
    }

    private fun expected(dataOffset: Int, chunkSize: Int) {
        val expected = ArrayList<Byte>()
        repeat(dataOffset) {
            expected.add(0)
        }
        for (i in dataHeader until dataLength + dataHeader) {
            if (expected.size % chunkSize == 0) {
                expected.add(-1)
            }
            expected.add(data[i])
        }
        this.expected = expected.toByteArray()
    }
}