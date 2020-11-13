package net.bald.netcdf

import net.bald.BinaryArray
import net.bald.Container
import org.apache.jena.shared.PrefixMapping
import ucar.nc2.*
import java.io.Closeable
import java.io.File
import java.lang.IllegalStateException

/**
 * NetCDF implementation of [BinaryArray].
 * Should be closed after use.
 */
class NetCdfBinaryArray(
    override val uri: String,
    private val file: NetcdfFile
): BinaryArray, Closeable {
    override val root: Container get() = container(file.rootGroup)
    override val prefixMapping: PrefixMapping get() = prefixMapping() ?: PrefixMapping.Factory.create()

    override fun close() {
        file.close()
    }

    private fun container(group: Group): Container {
        val prefixSrc = prefixSourceName()
        return NetCdfContainer(group, prefixSrc)
    }

    private fun prefixMapping(): PrefixMapping? {
        return prefixSource()?.let(::NetCdfPrefixMappingBuilder)?.build()
    }

    private fun prefixSource(): AttributeContainer? {
        return prefixSourceName()?.let { name ->
            file.findGroup(name)
                ?: file.findVariable(name)
                ?: throw IllegalStateException("Prefix group or variable $name not found.")
        }
    }

    private fun prefixSourceName(): String? {
        return file.findGlobalAttribute(Attribute.prefix)?.let { attr ->
            attr.stringValue
                ?: throw IllegalStateException("Global prefix attribute ${Attribute.prefix} must have a string value.")
        }
    }

    private object Attribute {
        const val prefix = "bald__isPrefixedBy"
    }

    companion object {
        /**
         * Instantiate a [BinaryArray] representation of the given NetCDF file and identifying URI.
         * The resulting [NetCdfBinaryArray] should be closed after use.
         * @param fileLoc The location of the NetCDF file on the local file system.
         * @param uri The URI which identifies the dataset.
         * @return A [BinaryArray] representation of the NetCDF file.
         */
        @JvmStatic
        fun create(fileLoc: String, uri: String? = null): NetCdfBinaryArray {
            val file = NetcdfFiles.open(fileLoc)
            val requiredUri = uri ?: uri(fileLoc)
            return create(file, requiredUri)
        }

        /**
         * @see [create].
         */
        @JvmStatic
        fun create(file: NetcdfFile, uri: String): NetCdfBinaryArray {
            return NetCdfBinaryArray(uri, file)
        }

        private fun uri(fileLoc: String): String {
            if (File.separatorChar != '/') {
                fileLoc.replace(File.separatorChar, '/')
            }
            return File(fileLoc).toPath().toUri().toString()
        }
    }
}