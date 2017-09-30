package pojo;

public class RpcRequest {

    private String requestId;
    private String interfaceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
    private String serviceVersion;

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String toString() {
        return "[ requestId = " + requestId + " , interfaceName = " + interfaceName + " , methodName = " + methodName
                + " , parameterTypes = " + (parameterTypes==null?"null":parameterTypes.toString())+ " , parameters = " + (parameters==null?"null":parameters.toString())
                + " , serviceVersion = " + serviceVersion + " ]";
    }
}

