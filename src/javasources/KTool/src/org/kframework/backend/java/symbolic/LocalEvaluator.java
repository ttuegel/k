// Copyright (c) 2013-2014 K Team. All Rights Reserved.
package org.kframework.backend.java.symbolic;

import org.kframework.backend.java.kil.*;
import org.kframework.kil.ASTNode;
import org.kframework.utils.errorsystem.KException;
import org.kframework.utils.errorsystem.KException.ExceptionType;
import org.kframework.utils.errorsystem.KException.KExceptionGroup;
import org.kframework.utils.errorsystem.KExceptionManager.KEMException;
import org.kframework.utils.general.GlobalSettings;

/**
 * Evaluates predicates and functions without doing tree traversal.
 * 
 * @author Traian
 */
public class LocalEvaluator extends LocalTransformer {
    
    /**
     * The symbolic constraint of the {@code ConstrainedTerm} which contains the
     * terms to be evaluated by this evaluator.
     */
    private final SymbolicConstraint constraint;

    public LocalEvaluator(TermContext context) {
        this(null, context);
    }

    public LocalEvaluator(SymbolicConstraint constraint, TermContext context) {
        super(context);
        this.constraint = constraint;
    }
    
    public SymbolicConstraint constraint() {
        return constraint;
    }
    
    private static String TRACE_MSG = "Function evaluation triggered infinite recursion. Trace:";
    
    @Override
    public ASTNode transform(KItem kItem) {
        try {
            // TODO(YilongL): shall we consider cache evaluation result in certain cases?
            Term evaluatedTerm = kItem.evaluateFunction(constraint, context);
            // TODO(YilongL): had to comment out the following assertion because the visitor/imp.k somehow fails here
    //        if (kItem.isGround() && kItem.isEvaluable(context)) {
    //            assert evaluatedTerm != kItem : "failed to evaluate function with ground arguments: " + kItem;
    //        }
            return evaluatedTerm;
        } catch (StackOverflowError e) {
            if (context.definition().context().globalOptions.debug) {
                e.printStackTrace();
            }
            GlobalSettings.kem.register(new KException(ExceptionType.ERROR, KExceptionGroup.CRITICAL,
                    TRACE_MSG));
            throw e; //unreachable
        } catch (KEMException e) {
            e.exception.addTraceFrame(kItem.kLabel().toString());
            throw e;
        }
    }

    @Override
    public ASTNode transform(KItemProjection kItemProjection) {
        return kItemProjection.evaluateProjection();
    }

    @Override
    public ASTNode transform(ListLookup listLookup) {
        return listLookup.evaluateLookup();
    }

    @Override
    public ASTNode transform(SetElementChoice setElementChoice) {
        return setElementChoice.evaluateChoice();
    }

    @Override
    public ASTNode transform(SetLookup setLookup) {
        return setLookup.evaluateLookup();
    }

    @Override
    public ASTNode transform(SetUpdate setUpdate) {
        return setUpdate.evaluateUpdate();
    }

    @Override
    public ASTNode transform(MapKeyChoice mapKeyChoice) {
        return mapKeyChoice.evaluateChoice();
    }

    @Override
    public ASTNode transform(MapLookup mapLookup) {
        return mapLookup.evaluateLookup();
    }

    @Override
    public ASTNode transform(MapUpdate mapUpdate) {
        return mapUpdate.evaluateUpdate(context);
    }

}
