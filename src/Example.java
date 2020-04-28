
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Example extends TestClass<String> implements TestInterface {

    public Example(String s, List<String> list) {
        super(s, list);
    }

    private static int c = 0;
    private final String INPUT_STRING = "second";

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                JustClass justClass = new JustClass(c, 1);
                List<String> strings = new ArrayList<>();
                Example example = new Example("first", strings);
                int k = 4;
                for (int i = 1; i < k; i++) {

                    if (k % i == 1) {
                        c = 1;
                    } else if (k % i == 0) {
                        c = 2;
                    }
                    justClass.increm();
                    switch (c) {
                        case 1: {
                            strings.add(example.iMethod(example.INPUT_STRING));
                            break;
                        }
                        case 2: {
                            new TestClass<>("num", strings);
                            break;
                        }
                        default: {
                            System.out.println("no such case");
                        }
                    }
                }

                for (String string : strings) {
                    System.out.println(string);
                }

                strings.sort(new Comparator<String>() {
                    @Override
                    public int compare(String s, String t1) {
                        return Integer.valueOf(s).compareTo(Integer.valueOf(t1));
                    }
                });

                example.defaultMethod();

            } catch (NumberFormatException e) {
                System.out.println("wrong string for sort");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("final");
            }

        }).start();
    }

    static final class JustClass {
        private int number, bound;

        JustClass(int number, int bound) {
            this.number = number;
            this.bound = bound;
        }

        void increm() {
            do {
                System.out.println(number++);
            } while (number < bound);

        }
    }

    @Override
    public String iMethod(String string) {
        return string + " after iMethod";
    }
}

class TestClass<T extends Serializable> {

    private final String TEST = "test ";

    public TestClass(T t, List<String> list) {
        list.add(this.TEST + t.toString());
    }
}

interface TestInterface {
    default void defaultMethod() throws Exception {
        System.out.println("default method call");
        throw new Exception();
    }

    String iMethod(String string);
}

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@interface TestAnnotation {
}