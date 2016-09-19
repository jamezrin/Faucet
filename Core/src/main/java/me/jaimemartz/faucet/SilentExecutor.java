package me.jaimemartz.faucet;

public abstract class SilentExecutor {
    public SilentExecutor() {
        try {
            execute();
        } catch (Exception e) {

        }
    }

    public abstract void execute() throws Exception;
}
