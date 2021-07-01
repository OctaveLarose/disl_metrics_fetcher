package disl_metrics_fetcher.helpers;

import ch.usi.dag.disl.staticcontext.MethodStaticContext;

public class MethodStaticContextPrinter extends MethodStaticContext
{
    public String scopeConcat(String scope, String toConcat) {
        if (scope.equals(""))
            return toConcat;
        else
            return scope.concat("/").concat(toConcat);
    }

    // could include synchronized, etc.
    public String getMethodScope() {
        String methodScope = "";

        if (this.isMethodPublic())
            methodScope = scopeConcat(methodScope, "pub");
        if (this.isMethodPrivate())
            methodScope = scopeConcat(methodScope, "pri");
        if (this.isMethodProtected())
            methodScope = scopeConcat(methodScope, "pro");
        if (this.isMethodConstructor())
            methodScope = scopeConcat(methodScope, "con");
        if (this.isMethodStatic())
            methodScope = scopeConcat(methodScope, "sta");
        if (this.isMethodFinal())
            methodScope = scopeConcat(methodScope, "fin");
        if (this.isMethodSynchronized())
            methodScope = scopeConcat(methodScope, "syn");

        return !methodScope.equals("") ? methodScope : "?";
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
