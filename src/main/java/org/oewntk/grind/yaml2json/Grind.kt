/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.grind.yaml2json

import org.oewntk.json.out.ModelConsumer
import org.oewntk.yaml.`in`.Factory
import java.io.File

/**
 * Main class that generates the WN database in the SQL format from the WNDB format
 *
 * @author Bernard Bou
 */
object Grind {

    /**
     * Main entry point
     *
     * @param args command-line arguments
     */
    @JvmStatic
    fun main(args: Array<String>) {
        val iArg = 0

        // Tracing
        val startTime = Tracing.start()

        // Input
        val inDir = File(args[iArg])
        Tracing.psInfo.println("[Input] " + inDir.absolutePath)

        // Input2
        val inDir2 = File(args[iArg + 1])
        Tracing.psInfo.println("[Input2] " + inDir2.absolutePath)

        // Output
        val outFile = File(args[iArg + 2])
        if (!outFile.exists()) {
            outFile.delete()
        }
        Tracing.psInfo.println("[Output] " + outFile.absolutePath)

        // Supply model
        Tracing.progress("before model is supplied,", startTime)
        val model = Factory(inDir, inDir2).get()
        //Tracing.psInfo.printf("[Model] %s%n%s%n%n", Arrays.toString(model.getSources()), model.info())
        Tracing.progress("after model is supplied,", startTime)

        // Consume model
        Tracing.progress("before model is consumed,", startTime)
        ModelConsumer(outFile).accept(model!!)
        Tracing.progress("after model is consumed,", startTime)

        // End
        Tracing.progress("total,", startTime)
    }
}
