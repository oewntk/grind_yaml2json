/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.grind.yaml2json

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import org.oewntk.yaml.`in`.Factory
import java.io.File
import org.oewntk.json.out.data.ModelConsumer as DataJsonModelConsumer
import org.oewntk.json.out.model.ModelConsumer as ModelJsonModelConsumer
import org.oewntk.json.out.oewn.ModelConsumer as OEWNJsonModelConsumer

/**
 * Main class that generates the WN database in the SQL format from the YAML format
 *
 * @author Bernard Bou
 */
object Grind {

    enum class SerializationMode {
        OEWN,
        DATA,
        MODEL
    }

    val serializationModeArg = ArgType.Choice(
        choices = SerializationMode.entries,
        variantToString = { it.name.lowercase() },
        toVariant = { raw ->
            when (raw.lowercase()) {
                "o", "oewn" -> SerializationMode.OEWN
                "d", "data" -> SerializationMode.DATA
                "m", "model" -> SerializationMode.MODEL
                else -> error("Unknown mode: $raw")
            }
        }
    )

    /**
     * Main entry point
     *
     * @param args command-line arguments
     */
    @JvmStatic
    fun main(args: Array<String>) {
        val parser = ArgParser("yaml2json")

        // Options (start with - or --)
        // @formatter:off
        val in1 by parser.argument(            ArgType.String,                                                         description = "Input dir or file")
        val in2 by parser.argument(            ArgType.String,                                                         description = "Extra input dir or file")
        val out by parser.argument(            ArgType.String,                                                         description = "Output dir or file")
        val outOne by parser.option(           ArgType.Boolean,       shortName = "o1", fullName = "out_one",          description = "Output one file")                 .default(false)
        val outMerge by parser.option(         ArgType.Boolean,       shortName = "m",  fullName = "merge",            description = "Do not group generated entries")  .default(false)
        val outPretty by parser.option(        ArgType.Boolean,       shortName = "op", fullName = "pretty",           description = "JSON pretty print")               .default(true)
        val outSerialization by parser.option( serializationModeArg,  shortName = "os", fullName = "serialization",    description = "Serialization mode")              .default(SerializationMode.OEWN)
        val verbose by parser.option(          ArgType.Boolean,       shortName = "v",  fullName = "verbose",          description = "Verbose output")                  .default(false)

        val traceTime by parser.option(        ArgType.Boolean,       shortName = "tt", fullName = "trace:time",       description = "trace time")                      .default(false)
        val traceHeap by parser.option(        ArgType.Boolean,       shortName = "th", fullName = "trace:heap",       description = "trace heap")                      .default(false)
        // @formatter:on
        parser.parse(args)

        // Tracing
        Tracing.traceTime = traceTime
        Tracing.traceHeap = traceHeap

        val startTime = Tracing.start()

        // Input
        val inDir = File(in1)
        Tracing.psInfo.println("[Input] " + inDir.absolutePath)

        // Input2
        val inDir2 = File(in2)
        Tracing.psInfo.println("[Input2] " + inDir2.absolutePath)

        // Output
        val outFile = File(out)
        val outDir = outFile.parentFile
        if (!outDir.exists()) {
            outDir.mkdirs()
        }
        if (outFile.exists() && !outFile.isDirectory) {
            outFile.delete()
        }
        Tracing.psInfo.println("[Output] " + outFile.absolutePath)

        // Supply model
        Tracing.progress("before model is supplied,", startTime)
        val model = Factory(inDir, inDir2, verbose = verbose).get()!!
        Tracing.progress("after model is supplied,", startTime)

        // Consume model
        Tracing.progress("before model is consumed,", startTime)
        when (outSerialization) {
            SerializationMode.OEWN -> OEWNJsonModelConsumer(outFile, split = !outOne, prettyPrint = outPretty, generated = !outMerge, verbose = verbose).accept(model)
            SerializationMode.DATA -> DataJsonModelConsumer(outFile, split = !outOne, prettyPrint = outPretty, verbose = verbose).accept(model)
            SerializationMode.MODEL -> ModelJsonModelConsumer(outFile, prettyPrint = outPretty, verbose = verbose).accept(model)
        }
        Tracing.progress("after model is consumed,", startTime)

        // End
        Tracing.progress("total,", startTime)
    }
}
