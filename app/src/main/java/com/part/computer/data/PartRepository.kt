package com.part.computer.data

import com.part.computer.model.OrderPart
import com.part.computer.model.PartOfData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

/**
 * Kelas Repository yang bertanggung jawab untuk mengelola dan menyediakan akses ke data terkait bagian-bagian komputer dan pesanan.
 */
class PartRepository {

    // Daftar untuk menyimpan instansi OrderPart yang mewakili bagian-bagian komputer yang tersedia dan jumlah pesanannya.
    private val orderParts = mutableListOf<OrderPart>()

    // Inisialisasi orderParts dengan data dummy jika awalnya kosong.
    init {
        if (orderParts.isEmpty()) {
            PartOfData.dummyTheParts.forEach {
                orderParts.add(OrderPart(it, 0))
            }
        }
    }

    /**
     * Mengembalikan Flow dari seluruh daftar instansi OrderPart, memungkinkan pemrograman reaktif dengan pembaruan data asinkron.
     */
    fun getAllRewards(): Flow<List<OrderPart>> {
        return flowOf(orderParts)
    }

    /**
     * Mengambil rewardId sebagai parameter dan mengembalikan OrderPart yang sesuai dari daftar orderParts.
     */
    fun getOrderRewardById(rewardId: Long): OrderPart {
        return orderParts.first {
            it.thePart.id == rewardId
        }
    }

    /**
     * Mengembalikan Flow hanya dari OrderPart yang jumlahnya tidak sama dengan nol.
     * Metode ini menyaring bagian-bagian tanpa pesanan terkait.
     */
    fun getAddedOrderRewards(): Flow<List<OrderPart>> {
        return getAllRewards()
            .map { orderRewards ->
                orderRewards.filter { orderReward ->
                    orderReward.count != 0
                }
            }
    }

    /**
     * Mengambil rewardId dan nilai count baru sebagai parameter. Memperbarui nilai count dari OrderPart dengan rewardId yang diberikan
     * dalam daftar orderParts. Mengembalikan Flow dengan nilai boolean yang menunjukkan apakah pembaruan berhasil.
     */
    fun updateOrderReward(rewardId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderParts.indexOfFirst { it.thePart.id == rewardId }
        val result = if (index >= 0) {
            val orderReward = orderParts[index]
            orderParts[index] = orderReward.copy(thePart = orderReward.thePart, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        // Variabel volatile yang dapat bernilai null untuk menyimpan instansi tunggal dari PartRepository.
        @Volatile
        private var instance: PartRepository? = null

        /**
         * Mengembalikan instansi tunggal dari PartRepository. Jika instansi belum diinisialisasi,
         * membuat instansi baru menggunakan blok synchronized untuk memastikan keselamatan thread.
         */
        fun getInstance(): PartRepository =
            instance ?: synchronized(this) {
                PartRepository().apply {
                    instance = this
                }
            }
    }
}
