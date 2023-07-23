@file:OptIn(ExperimentalSerializationApi::class)
package kjm.fit.converter.utils.proto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor


/**
 * Generate protocol buffers schema text for the given [descriptors] using the DSL.
 */
@ExperimentalSerializationApi
fun generateProtoBufSchema(block: ProtoBufSchemaBuilder.() -> Unit): String {
    val builder = ProtoBufSchemaBuilder()
    builder.block()
    return builder.build()
}

/**
 * DSL Builder class for generating protocol buffers schema.
 */
class ProtoBufSchemaBuilder {
    private val descriptors = mutableListOf<SerialDescriptor>()
    private var packageName: String? = null
    private val options = mutableMapOf<String, String>()
    private var protoVersion = ProtoBufSchemaGenerator.ProtoVersion.v2

    /**
     * Add a root serializable descriptor to the schema.
     */
    fun descriptor(descriptor: SerialDescriptor) {
        descriptors.add(descriptor)
    }

    /**
     * Set the common package name for all messages and enums in the schema.
     */
    fun packageName(name: String) {
        packageName = name
    }

    /**
     * Add an option to the schema.
     */
    fun option(name: String, value: String) {
        options[name] = value
    }

    /**
     * Set the protocol buffers version for the schema.
     */
    fun protoVersion(version: ProtoBufSchemaGenerator.ProtoVersion) {
        protoVersion = version
    }

    /**
     * Build and return the protocol buffers schema text.
     */
    @ExperimentalSerializationApi
    fun build(): String {
        return ProtoBufSchemaGenerator.generateSchemaText(descriptors, packageName, options, protoVersion)
    }
}
