package com.example.vaibhavchoudhary.betterlogger;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Created by vaibhavchoudhary on 01/03/16.
 * call PractoLogger.init in onCreate activity to instantiate the instance
 * call PractoLogger.close to flush and close the file if its not done .lck (lock) file will remain there
 */
public class PractoLogger {


    public static final int LOG_LEVEL_START_POS = Level.SEVERE.intValue() + 100;

    private  static final String LOG_TAG = PractoLogger.class.getSimpleName();

    private static boolean DEBUG_MODE = true; // to print debug logs or not

    private static final int FILE_SIZE = 2097152; // Maximum size of file 2mb
    private static final int FILE_COUNT = 5;    // Maximum number of files
    private static final boolean APPEND_TO_FILES = true; // append to existing files

    private final FileHandler mLogFileHandler;
    private final Calendar calender;
    private Logger logger;

    private static PractoLogger practoLogger;

    private final SimpleDateFormat simpleDateFormat;


    private PractoLogger(Context context) throws IOException {

        logger = Logger.getLogger(LOG_TAG);
        File baseDir = context.getFilesDir();
        File logsDir = new File(baseDir, "PractoLogs");
        if(!logsDir.exists()){
            // create a separate directory for logging
            logsDir.mkdirs();
        }
        // files will be created as data/data/PackageName/files/PractoLogs/PractoLoggerX.log -> x is number of file from o to Max_file_count
        mLogFileHandler = new FileHandler(logsDir.getAbsoluteFile()+"/" + PractoLogger.class.getSimpleName() + "%g.log",
                FILE_SIZE, FILE_COUNT, APPEND_TO_FILES);

        // specifies logs to output
        mLogFileHandler.setFormatter(new MyCustomFormatter());

        // set parent handlers to false so that android default log does not appear
        logger.setUseParentHandlers(false);

        logger.addHandler(mLogFileHandler);
        calender = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    }

    // initializes  class's single instance
    public static void init(Context context) throws IOException {
        practoLogger = new PractoLogger(context);
    }


    // info log
    public static void i(String Tag, String message){
        if(practoLogger == null){
            // revert back to android Log.X if class instantiation
            Log.i(Tag, message);
            return;
        }
        Log.i(Tag, message);
        LogRecord record = new LogRecord(InfoLevel.INFO_LEVEL, message);
        record.setSourceClassName(Tag);
        practoLogger.logger.log(record);
    }

        // info log
    public static void i(String Tag, String message, Throwable tr){
        if(practoLogger == null){
            Log.i(Tag, message, tr);
            return;
        }

        Log.i(Tag, message, tr);
        LogRecord record = new LogRecord(InfoLevel.INFO_LEVEL, message  + "\t" + throwableStackTrace(tr));
        record.setSourceClassName(Tag);
        practoLogger.logger.log(record);
    }

    // debug log
    public static void d(String Tag, String message){
        if(practoLogger == null){
            // revert back to android Log.X if class instantiation
            Log.d(Tag, message);
            return;
        }
        if(DEBUG_MODE){
            Log.d(Tag, message);
            LogRecord record = new LogRecord(DebugLevel.DEBUG_LEVEL, message);
            record.setSourceClassName(Tag);
            practoLogger.logger.log(record);
        }
    }

    // debug log
    public static void d(String Tag, String message, Throwable tr){
        if(practoLogger == null){
            Log.d(Tag, message, tr);
            return;
        }
        if(DEBUG_MODE){
            Log.d(Tag, message, tr);
            LogRecord record = new LogRecord(DebugLevel.DEBUG_LEVEL, message + "\t" + throwableStackTrace(tr));
            record.setSourceClassName(Tag);
            practoLogger.logger.log(record);
        }
    }

    // verbose log
    public static void v(String Tag, String message){
        if(practoLogger == null){
            Log.v(Tag, message);
            return;
        }
        Log.v(Tag, message);
        LogRecord record = new LogRecord(VerboseLevel.VERBOSE_LEVEL, message);
        record.setSourceClassName(Tag);
        practoLogger.logger.log(record);
    }

    // verbose log
    public static void v(String Tag, String message, Throwable tr){
        if(practoLogger == null){
            Log.v(Tag, message, tr);
            return;
        }
        Log.v(Tag, message, tr);
        LogRecord record = new LogRecord(VerboseLevel.VERBOSE_LEVEL, message + "\t" + throwableStackTrace(tr));
        record.setSourceClassName(Tag);
        practoLogger.logger.log(record);
    }

    // warn log
    public static void w(String Tag, String message){
        if(practoLogger == null){
            Log.w(Tag, message);
            return;
        }
        Log.w(Tag, message);
        LogRecord record = new LogRecord(WarnLevel.WARN_LEVEL, message);
        record.setSourceClassName(Tag);
        practoLogger.logger.log(record);
    }

    // warn log
    public static void w(String Tag, String message, Throwable tr){
        if(practoLogger == null){
            Log.w(Tag, message, tr);
            return;
        }
        Log.w(Tag, message, tr);
        LogRecord record = new LogRecord(WarnLevel.WARN_LEVEL, message + "\t" + throwableStackTrace(tr));
        record.setSourceClassName(Tag);
        practoLogger.logger.log(record);
    }

    // error log
    public static void e (String Tag, String message){
        if(practoLogger == null){
            Log.e(Tag, message);
            return;
        }
        Log.e(Tag, message);
        LogRecord record = new LogRecord(ErrorLevel.ERROR_LEVEL, message);
        record.setSourceClassName(Tag);
        practoLogger.logger.log(record);
    }

    // error log with throwable
    public static void e (String Tag, String message, Throwable tr){
        if(practoLogger == null){
            Log.e(Tag, message);
            return;
        }
        Log.e(Tag, message, tr);
        LogRecord record = new LogRecord(ErrorLevel.ERROR_LEVEL, message + "\t" + throwableStackTrace(tr));
        record.setSourceClassName(Tag);
        practoLogger.logger.log(record);
    }


    public static String throwableStackTrace(Throwable tr){
        StringWriter errors = new StringWriter();
        tr.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    public static void close(){
        Log.i(PractoLogger.class.getSimpleName(), "Closing handler");
        practoLogger.mLogFileHandler.flush();
        practoLogger.mLogFileHandler.close();
        practoLogger = null;
    }

    public class MyCustomFormatter extends Formatter {

        @Override
        public String format(LogRecord r) {
            // compose your message here
            String sep = "\t";
            String time = simpleDateFormat.format(calender.getTime());
            String level = r.getLevel().toString();
            String tag = r.getSourceClassName();
            String threadName  = Thread.currentThread().getName();
            String message = r.getMessage();
            return time + sep
                    + level + sep
                    + threadName + sep
                    + tag + sep
                    + message + "\n\n";
        }
    }


}



class InfoLevel extends Level{

    public static final Level INFO_LEVEL = new InfoLevel("I", PractoLogger.LOG_LEVEL_START_POS + 1 );

    public InfoLevel(String name, int level) {
        super(name, level);
    }
}
class DebugLevel extends Level{

    public static final Level DEBUG_LEVEL  = new DebugLevel("D", PractoLogger.LOG_LEVEL_START_POS + 2 );

    public DebugLevel(String name, int level) {
        super(name, level);
    }
}
class VerboseLevel extends Level{

    public static final Level VERBOSE_LEVEL  = new VerboseLevel("V", PractoLogger.LOG_LEVEL_START_POS + 3 );

    public VerboseLevel(String name, int level) {
        super(name, level);
    }
}
class WarnLevel extends Level{

    public static final Level WARN_LEVEL  = new WarnLevel("W", PractoLogger.LOG_LEVEL_START_POS + 4 );

    public WarnLevel(String name, int level) {
        super(name, level);
    }
}

class ErrorLevel extends Level{

    public static final Level ERROR_LEVEL  = new ErrorLevel("E", PractoLogger.LOG_LEVEL_START_POS + 5 );

    public ErrorLevel(String name, int level) {
        super(name, level);
    }
}


