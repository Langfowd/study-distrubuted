package protocol;

import java.io.Serializable;

/**
 * 自定义协议
 */
public class HandlerProtocol implements Serializable {
    private Class[] paramTypes;
    private Object[] paramValues;
    private String className;
    private String methodName;

    /**
     * Gets the value of paramTypes.
     *
     * @return the value of paramTypes
     */
    public Class[] getParamTypes() {
        return paramTypes;
    }

    /**
     * Sets the paramTypes.
     *
     * @param paramTypes paramTypes
     */
    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    /**
     * Gets the value of paramValues.
     *
     * @return the value of paramValues
     */
    public Object[] getParamValues() {
        return paramValues;
    }

    /**
     * Sets the paramValues.
     *
     * @param paramValues paramValues
     */
    public void setParamValues(Object[] paramValues) {
        this.paramValues = paramValues;
    }

    /**
     * Gets the value of className.
     *
     * @return the value of className
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the className.
     *
     * @param className className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets the value of methodName.
     *
     * @return the value of methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Sets the methodName.
     *
     * @param methodName methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
