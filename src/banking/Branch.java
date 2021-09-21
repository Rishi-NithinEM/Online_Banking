package banking;

public class Branch {


    public static enum BrachName {

        MADURAI("HDFC_1"),
        CHENNAI("HDFC_2"),
        COIMBATORE("HDFC_3");


        private String IFSC;

        BrachName(String st) {
            this.IFSC = st;
        }

        public String getIFSCCode() {
            return this.IFSC;
        }
    }

}
