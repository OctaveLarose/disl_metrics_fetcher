package disl_metrics_fetcher;

import ch.usi.dag.disl.staticcontext.MethodStaticContext;

public class MethodStaticContextPrinter extends MethodStaticContext
{
    // could include static, synchronized, etc.
    public String getMethodScope() {
        if (this.isMethodPublic())
            return "pub";
        if (this.isMethodPrivate())
            return "pri";
        if (this.isMethodProtected())
            return "pro";
        return "?";
    }

    public String getMethodEntryStr() {
        return "> " + this.getMethodDescriptionStr();
    }

    public String getMethodExitStr() {
        return "< " + this.getMethodDescriptionStr();
    }

    public String getMethodDescriptionStr() {
        return this.getMethodScope()
                + " "
                + this.thisMethodDescriptor() + " "
                + this.thisMethodFullName();
    }
}
