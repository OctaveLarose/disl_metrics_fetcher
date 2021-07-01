import ch.usi.dag.disl.annotation.*;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
import disl_metrics_fetcher.helpers.MethodStaticContextPrinter;

import java.io.FileWriter;
import java.io.IOException;

public class CTDiSLClass {

    @SyntheticLocal
    static long entryTime;

    @SyntheticLocal
    static FileWriter logFile;

    static {
        try {
            logFile = new FileWriter("logfile.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Before (marker=BodyMarker.class, guard = NonDefaultGuard.class)
//    static void conMethodEntryLog(MethodStaticContextPrinter mscp) {
//        try {
//            entryTime = System.nanoTime();
//            logFile.write(mscp.getMethodEntryStr() + "\n");
//            logFile.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Before (marker=BodyMarker.class, guard = NonDefaultGuard.class)
    static void conMethodEntryPrint(MethodStaticContextPrinter mscp) {
        System.out.println(mscp.getMethodEntryStr() + "\n");
    }



//    @After (marker=BodyMarker.class, guard = NonDefaultGuard.class)
//    static void conMethodExitLog(MethodStaticContextPrinter mscp) {
//        try {
//            logFile.write(mscp.getMethodExitStr() + " (" + (System.nanoTime() - entryTime) + "ns)\n");
//            logFile.flush();
//            logFile.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @After (marker=BodyMarker.class, guard = NonDefaultGuard.class)
    static void conMethodExitPrint(MethodStaticContextPrinter mscp) {
        System.out.println(mscp.getMethodExitStr() + " (" + (System.nanoTime() - entryTime) + "ns)\n");
    }

    public static final class NonDefaultGuard {
        @GuardMethod
        public static boolean isNotDefault(MethodStaticContext context) {
            String[] blacklist = {"java", "sun", "jdk"};

            for (String bannedStr: blacklist) {
                if (context.thisMethodFullName().startsWith(bannedStr))
                        return false;
            }
            return true;
        }
    }
}
