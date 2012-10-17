package com.jxls.plus.expression;




import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: Nov 2, 2009
 *
 * @author Leonid Vysochyn
 */
public class JexlExpressionEvaluator implements ExpressionEvaluator{
    private Expression jexlExpression;
    private static JexlEngine jexl = new JexlEngine();
    JexlContext jexlContext;
    static Map<String, Expression> expressionMap = new HashMap<String, Expression>();

    public JexlExpressionEvaluator(String expression) {
        jexlExpression = jexl.createExpression( expression );
    }

    public JexlExpressionEvaluator(Map<String, Object> context) {
        jexlContext = new MapContext(context);
    }

    public JexlExpressionEvaluator(JexlContext jexlContext) {
        this.jexlContext = jexlContext;
    }

    public Object evaluate(String expression) {
        try {
            Expression jexlExpression =  expressionMap.get(expression);
            if( jexlExpression == null ){
                jexlExpression = jexl.createExpression( expression );
                expressionMap.put(expression, jexlExpression);
            }
            return jexlExpression.evaluate(jexlContext);
        } catch (Exception e) {
            throw new EvaluationException("An error occurred when evaluating expression " + expression, e);
        }
    }
    
    public Object evaluate(Map<String, Object> context){
        jexlContext = new MapContext(context);
        try {
            return jexlExpression.evaluate( jexlContext );
        } catch (Exception e) {
            throw new EvaluationException("An error occurred when evaluating expression " + jexlExpression.getExpression(), e);
        }
    }

    public Expression getJexlExpression() {
        return jexlExpression;
    }
}