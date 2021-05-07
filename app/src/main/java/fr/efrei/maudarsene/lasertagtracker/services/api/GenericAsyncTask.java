package fr.efrei.maudarsene.lasertagtracker.services.api;

import android.os.AsyncTask;

import java.util.function.Consumer;
import java.util.function.Function;

public class GenericAsyncTask<TParam, TResult> extends AsyncTask<TParam, Void, TResult> {

    private Function<TParam, TResult> executeHandler;
    private Consumer<TResult> postExecuteHandler;

    public GenericAsyncTask(Function<TParam, TResult> executeHandler, Consumer<TResult> postExecuteHandler) {
        this.executeHandler = executeHandler;
        this.postExecuteHandler = postExecuteHandler;
    }

    @Override
    protected TResult doInBackground(TParam... tParams) {
        return executeHandler.apply(tParams[0]);
    }

    @Override
    protected void onPostExecute(TResult tResult) {
        postExecuteHandler.accept(tResult);
    }
}
