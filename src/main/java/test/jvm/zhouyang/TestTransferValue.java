package test.jvm.zhouyang;

public class TestTransferValue {
    public static void changeValue1(int age){
        age = 20;
    }
    public static void changeValue2(Person person){
        person.setPersonName("xxx");
    }
    public static void changeValue3(String str){
        str = "xxx";
    }

    public static void main(String[] args) {
        TestTransferValue test = new TestTransferValue();

        int age = 30;
        test.changeValue1(age);
        System.out.println(age);

        Person person = new Person("abc");
        test.changeValue2(person);
        System.out.println(person.getPersonName());

        String name = "abc";
        test.changeValue3(name);
        System.out.println(name);



    }
}
