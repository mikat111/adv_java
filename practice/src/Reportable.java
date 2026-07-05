public interface Reportable {

    default void printHeader(){
        System.out.println("===UNIVERSITY REPORT===");
    }

    static void printFooter(){
        System.out.println("===END OF REPORT===");
    }

    private String decorate (String text){
        return "***" + text + "***";
    }

    void generateReport();
}
