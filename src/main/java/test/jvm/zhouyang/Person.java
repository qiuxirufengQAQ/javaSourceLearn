package test.jvm.zhouyang;

public class Person {

    private String personName;

    public Person() {
    }

    public Person(String personName) {
        this.personName = personName;
    }

    /**
     * 获取
     *
     * @return personName
     */
    public String getPersonName() {
        return this.personName;
    }

    /**
     * 设置
     *
     * @param personName
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
