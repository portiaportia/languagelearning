package com.model;

import java.time.LocalDate;

public class Loan {
    private User user;
    private Book book;
    private LocalDate dueDate;
    private int renewCount;

    public Loan(User user, Book book, LocalDate dueDate, int renewCount){
        this.user = user;
        this.book = book;
        this.dueDate = dueDate;
        this.renewCount = renewCount;
    }

    public User getUser(){
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getDueDate(){
        return dueDate;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public boolean renew(){
        if(renewCount < 1){
            return false;
        }

        renewCount--;
        dueDate = dueDate.plusMonths(1);
        return true;
    }

    public String toString(){
        return user.getFirstName() + " " + user.getLastName() + " " + book + " " + dueDate.toString();
    }
}
