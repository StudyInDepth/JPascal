package wci.backend.interpreter;

import wci.backend.*;
import wci.backend.interpreter.executors.*;
import wci.intermediate.*;
import wci.intermediate.icodeimpl.*;
import wci.message.*;

import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static wci.message.MessageType.INTERPRETER_SUMMARY;

/**
 * <h1>Executor</h1>
 *
 * <p>The executor for an interpreter back end.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class Executor extends Backend
{
    protected static int executionCount;
    protected static RuntimeErrorHandler errorHandler;

    static {
        executionCount = 0;
        errorHandler = new RuntimeErrorHandler();
    }

    /**
     * Constructor.
     */
    public Executor() {}

    /**
     * Constructor for subclasses.
     * @param the parent executor.
     */
    public Executor(Executor parent)
    {
        super();
    }

    /**
     * Getter.
     * @return the error handler.
     */
    public RuntimeErrorHandler getErrorHandler()
    {
        return errorHandler;
    }

    /**
     * Execute the source program by processing the intermediate code
     * and the symbol table stack generated by the parser.
     * @param iCode the intermediate code.
     * @param symTabStack the symbol table stack.
     * @throws Exception if an error occurred.
     */
    public void process(ICode iCode, SymTabStack symTabStack)
        throws Exception
    {
        this.symTabStack = symTabStack;
        this.iCode = iCode;

        long startTime = System.currentTimeMillis();

        // Get the root node of the intermediate code and execute.
        ICodeNode rootNode = iCode.getRoot();
        StatementExecutor statementExecutor = new StatementExecutor(this);
        statementExecutor.execute(rootNode);

        float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
        int runtimeErrors = errorHandler.getErrorCount();

        // Send the interpreter summary message.
        sendMessage(new Message(INTERPRETER_SUMMARY,
                                new Number[] {executionCount,
                                              runtimeErrors,
                                              elapsedTime}));
    }
}
