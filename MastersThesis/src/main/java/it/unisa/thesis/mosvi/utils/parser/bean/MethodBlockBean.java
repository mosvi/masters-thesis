package it.unisa.thesis.mosvi.utils.parser.bean;

public class MethodBlockBean extends ComponentBean{

    private MethodBean belongingMethod;

    public MethodBlockBean() {
        super("BLOCK");
    }

    public void setBelongingMethod(MethodBean belongingMethod) {
        this.belongingMethod = belongingMethod;
    }

    @Override
    public String getQualifiedName() {
        return belongingMethod.getQualifiedName() + "." + name;
    }
}
