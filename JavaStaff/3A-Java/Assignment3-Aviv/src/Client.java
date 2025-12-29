public class Client {
    private String userName;
    private String password;
    private String academicStatus;
    private char yearsOfStd;
    private boolean registered;

    public Client(String userName) {
        this.userName=userName;
    }

    //checking the password requirement
     public boolean checkPassword(String password) {
        char [] passwordChar=password.toCharArray();
        boolean isUpper =false;
        boolean isLower=false;
        boolean isDigit=false;

        if (passwordChar.length < 9) {
            return false;
        }
        for (char i:passwordChar ) {
            if (Character.isUpperCase(i)) {
                isUpper=true;
            } else if (Character.isLowerCase(i)) {
                isLower=true;

            } else if (Character.isDigit(i)) {
                isDigit=true;
            }
        }
        return isUpper && isLower && isDigit;
    }

    public void setPassword(String password) {
       this.password=password;

    }
    public String getPassword() {
        return this.password;
    }
    public void setUserName(String userName) {
        this.userName=userName;
    }
    public String getUserName() {
        return userName;
    }
    public void setAcademicStatus(String academicStatus) {
        this.academicStatus=academicStatus;
     }
     public String getAcademicStatus() {
        return academicStatus;
     }
     public void setYearsOfStd(char yearsOfStd) {
        this.yearsOfStd=yearsOfStd;
     }
     public char getYearsOfStd() {
        return yearsOfStd;
     }

    public boolean equals(Object obj){
        if (obj instanceof Client) {
            return this.userName.equals(((Client)obj).userName);
        }
        return false;

    }






}




