package pinpointrules;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.logging.Level;

public class ConditionalLogging {

    private static final org.slf4j.Logger LOG = LoggerFactory
            .getLogger(ConditionalLogging.class);
    private static final java.util.logging.Logger JUTIL_LOG = java.util.logging.Logger
            .getLogger(ConditionalLogging.class.toString());
    private static final Log COMMONS_LOG = LogFactory.getLog(ConditionalLogging.class);
    private static final Logger logger = LoggerFactory.getLogger(ConditionalLogging.class);

    public void testUnConditionalConcatenation(String key, String faultCode) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Trying to get key [" + key + "] or ["
                    + faultCode.toUpperCase(Locale.US)
                    + "] in the given order.");
        }
        if (JUTIL_LOG.isLoggable(Level.INFO)) {
            JUTIL_LOG.info("Trying to get key [" + key + "] or ["
                    + faultCode.toUpperCase(Locale.US)
                    + "] in the given order.");
        }
        LOG.debug("Trying to get key [" + key + "] or ["
                + faultCode.toUpperCase(Locale.US) + "] in the given order.");
        JUTIL_LOG.info("Trying to get key [" + key + "] or ["
                + faultCode.toUpperCase(Locale.US) + "] in the given order.");
    }

    public void testUnConditionalStringOperations() {
        StringBuffer sb = new StringBuffer();
        long totalMem = Runtime.getRuntime().totalMemory();
        long freeMem = Runtime.getRuntime().freeMemory();
        LOG.debug(String
                .format("TotalMem= %s , FreeMem= %s", totalMem, freeMem)
                + ", usedMem [MB]= "
                + String.valueOf((totalMem - freeMem) / 1000000));
        LOG.debug(String
                .format("TotalMem= %s , FreeMem= %s", totalMem, freeMem));
        LOG.debug("buffer = {}", sb.toString());
    }

    public void testUnConditionalBuiltLogStringDefect1() {
        StringBuilder logStatement = new StringBuilder();
        List<String> values = Arrays
                .asList("tic", "tac", "toe");
        for (String val : values) {
            logStatement.append(val + ", ");
        }
        LOG.debug("logStatement: {}.", logStatement);
    }

    public void testUnConditionalBuiltLogStringDefect2() {
        StringBuilder logStatement = new StringBuilder();
        List<String> values = Arrays
                .asList("tic", "tac", "toe");
        Iterator<String> iter = values.iterator();
        while (iter.hasNext()) {
            logStatement.append(iter.next() + ", ");
        }
        LOG.trace("logStatement: {}.", logStatement);
    }

    public void testUnConditionalBuiltLogStringDefect3() {
        StringBuilder logStatement = new StringBuilder();
        List<String> values = Arrays
                .asList("tic", "tac", "toe");
        Iterator<String> iter = values.iterator();
        do {
            logStatement.append(iter.next() + ", ");
        } while (iter.hasNext());

        LOG.info("logStatement: {}.", logStatement);
    }

    public void testUnConditionalBuiltLogStringDefect4() {
        StringBuilder logStatement = new StringBuilder();
        List<String> values = Arrays
                .asList("tic", "tac", "toe");
        int i = 0;
        for (String val : values) {
            i++;
            logStatement.append(val).append(", ");
        }
        LOG.debug("logStatement: {}.{}", logStatement, "other");

    }


    public void testUnConditionalBuiltLogStringDefect5() {
        StringBuilder logStatement = new StringBuilder();
        List<String> values = Arrays.asList("tic", "tac", "toe");
        for (String val : values) {
            logStatement.append(val);
        }
        LOG.debug("values: {}.", values);
        LOG.debug("logStatement: {}.", logStatement);

    }

    public void testUnConditionalBuiltLogStringDefect6() {
        StringBuilder logStatement = new StringBuilder();
        StringBuilder statement = new StringBuilder();
        List<String> values = Arrays
                .asList("tic", "tac", "toe");
        for (String val : values) {
            LOG.debug("statement: {}.", statement);
            logStatement.append(val);
        }
        LOG.debug("statement: {}.", statement);
        LOG.debug("logStatement: {}.", logStatement);

    }

    public void testUnConditionalBuiltLogStringDefect7() {
        StringBuilder logStmt = new StringBuilder();
        StringBuilder statement = new StringBuilder();
        List<String> values = Arrays
                .asList("tic", "tac", "toe");

        logStmt.append(values.get(0));
        LOG.debug("statement: {}.", statement);
        LOG.debug("logStatement: {}.", logStmt);

    }

    public void testUnConditionalBuiltLogStringCorrectDefectInAppend() {
        StringBuilder statement = new StringBuilder();
        List<String> values = Arrays
                .asList("tic", "tac", "toe");
        for (String val : values) {
            statement.append(val + ", ");
        }
        LOG.debug("logStatement: {}.", statement);
        // do something else with it
        testUnConditionalConcatenation(statement.toString(),
                statement.toString());
    }

    public void testUnConditionalBuiltLogStringCorrect2() {
        StringBuilder statement = new StringBuilder();
        // build conditionally
        if (LOG.isDebugEnabled()) {
            List<String> values = Arrays.asList("tic", "tac",
                    "toe");
            for (String val : values) {
                statement.append(val);
            }
        }
        LOG.debug("logStatement: {}.", statement);
    }

    public void testUnConditionalBuiltLogStringCorrect3() {
        StringBuilder statement = new StringBuilder();
        List<String> values = Arrays
                .asList("tic", "tac", "toe");
        for (String val : values) {
            statement.append(val);
        }
        // no logging
    }

    public void testUnConditionalBuiltLogStringCorrect4() {
        StringBuilder statement = new StringBuilder();
        List<String> values = Arrays
                .asList("tic", "tac", "toe");
        for (String val : values) {
            statement.append(val);
        }
        statement.append(values.get(0));
        LOG.debug("logStatement: {}.", statement);
        // do something else with it

        testUnConditionalConcatenation(statement.toString(),
                statement.toString());

    }

    public void testUnConditionalBuiltLogStringCorrect5() {
        StringBuilder statement = new StringBuilder();
        List<String> values = Arrays
                .asList("tic", "tac", "toe");
        for (String val : values) {
            statement.append(val);
        }
        statement.append(values.get(0));
        LOG.debug("logStatement: {}.", statement);
        // do something else with it
        try {
            this.testUnConditionalConcatenation(statement.toString(),
                    statement.toString());
        } catch (Exception e) {
        }
    }

    public void testUnConditionalBuiltLogStringCorrect6() {
        StringBuilder statement9 = new StringBuilder();
        // build conditionally
        if (LOG.isErrorEnabled()) {
            List<String> values = Arrays.asList("tic", "tac",
                    "toe");
            for (String val : values) {
                statement9.append(val);
            }
        }
        LOG.debug("logStatement: {}.", statement9);
    }

    public void testUnConditionalBuiltLogStringCorrect7() { // not really correct, yet, no violation by this rule now
        String logStatement = "";
        List<String> values = Arrays.asList("tic", "tac", "toe");
        for (String val : values) {
            logStatement = logStatement + val + ", "; // should flag in another
            // rule first:
            // AvoidStringCancatInLoop
        }
        LOG.debug("logStatement: {}.", logStatement);
    }

    public void redirect() throws Exception {

        final StringBuilder reconstructedUrl = new StringBuilder("url");
        reconstructedUrl.append("a");
        reconstructedUrl.append("b");

        final Integer statusCode = 0;
        LOG.debug("Send redirect to url: '{}', status-code '{}'.",
                reconstructedUrl, statusCode);

        final FrameworkResponse response = new FrameworkResponse();
        try {
            response.sendRedirect(statusCode, reconstructedUrl.toString());
        } catch (final Exception e) {
            LOG.warn("Could not redirect, message: '{}'.", e.getMessage());
            throw new Exception(e);
        }
    }

    public void testUnconditionalCommonLogging(String wraAuthenticationToken) {
        COMMONS_LOG.debug("Entering doFilter : " + wraAuthenticationToken);
    }

    public static void testRMLiteralsCorrect() {
        logger.trace("No Configuration result for query with requestorId {}, jellyType {}, jellySubType {}, "
                + "configurationKey {}, and date {}");
    }

    public static void testCorrectPCC_92() {
        HttpServletRequest request = null;
        final StringBuilder headers = new StringBuilder();
        if (LOG.isDebugEnabled()) {
            final Enumeration enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                final String name = (String) enumeration.nextElement();
                headers.append(name);
                headers.append("=");
            }
        }
        LOG.debug("headers: {}.", headers);
    }

    static class FrameworkResponse {
        void sendRedirect(Integer status, String url) throws Exception {

        }
    }


}
