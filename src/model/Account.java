package model;

import enums.AssessesType;
import exceptions.InvalidAccountDetailsException;
import validator.AccountValidator;

public class Account {
    private String panNumber;
    private String name;
    private String place;

    public static class AccountBuilder{
        private String panNumber;
        private String name;
        private String place;

        public AccountBuilder setPanNumber(String panNumber) {
            this.panNumber = panNumber;
            return this;
        }

        public AccountBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AccountBuilder setPlace(String place) {
            this.place = place;
            return this;
        }

        public Account build(){
            try{
                AccountValidator.validateName(this.name);
                AccountValidator.validatePan(this.panNumber);
                AccountValidator.validatePlace(this.place);
            }
            catch (Exception e){
                throw new InvalidAccountDetailsException(e.getMessage());
            }
            return new Account(this);
        }
    }

    private Account(AccountBuilder accountBuilder){
        this.name = accountBuilder.name;
        this.place = accountBuilder.place;
        this.panNumber = accountBuilder.panNumber;
    }
    public static AccountBuilder builder(){
        return new AccountBuilder();
    }

    public String getPanNumber() {
        return panNumber;
    }

    public Account setPanNumber(String panNumber) {
        this.panNumber = panNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
    }

    public String getPlace() {
        return place;
    }

    public Account setPlace(String place) {
        this.place = place;
        return this;
    }
}
