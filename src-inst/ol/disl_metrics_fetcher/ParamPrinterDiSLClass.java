import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.marker.BodyMarker;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
import disl_metrics_fetcher.helpers.ParamPrinterContext;

public class ParamPrinterDiSLClass {
    @Before(marker= BodyMarker.class, guard = MethodInstrDiSLClass.NonDefaultGuard.class)
    static void modifyAllMethods(ParamPrinterContext dlc, MethodStaticContext msc, DynamicContext di) {
        dlc.modifyMethodToPrint();
    }
}
