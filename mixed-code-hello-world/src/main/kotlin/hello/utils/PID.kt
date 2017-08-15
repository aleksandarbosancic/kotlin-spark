package hello.utils

import java.lang.management.ManagementFactory

class PID {

    fun printPID(): Unit {
        val vmName = ManagementFactory.getRuntimeMXBean().name
        val p = vmName.indexOf("@")
        val pid = vmName.substring(0, p)
        println("PID :" + pid)
    }

}