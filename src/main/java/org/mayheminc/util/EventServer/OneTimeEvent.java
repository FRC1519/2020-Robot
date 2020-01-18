package org.mayheminc.util.EventServer;

public abstract class OneTimeEvent extends Event {
    boolean hasExecuted = false;

    public String Execute() {
        if (hasExecuted) {
            return "";
        }
        String S = this.OneTimeExecute();
        if (S != "") {
            hasExecuted = true;
        }
        return S;
    }

    abstract public String OneTimeExecute();
}