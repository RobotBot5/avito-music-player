package com.robotbot.avito.music_api

import android.net.Uri
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.TransferListener

@UnstableApi
class SlowDataSource(
    private val delegate: DataSource,
    private val delayMillis: Long = 50L // задержка в миллисекундах
) : DataSource {

    override fun open(dataSpec: DataSpec): Long {
        return delegate.open(dataSpec)
    }

    override fun read(buffer: ByteArray, offset: Int, readLength: Int): Int {
        // Добавляем задержку перед чтением данных
        try {
            Thread.sleep(delayMillis)
        } catch (e: InterruptedException) {
            // Можно обработать, если нужно
        }
        return delegate.read(buffer, offset, readLength)
    }

    override fun addTransferListener(transferListener: TransferListener) {
        TODO("Not yet implemented")
    }

    override fun getUri(): Uri? = delegate.uri

    override fun close() {
        delegate.close()
    }
}