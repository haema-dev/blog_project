package me.blog.haema;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Hello {

    private final int num;


    public static void main(String[] args) {

        Hello hello = new Hello(1);
        System.out.println(hello.num);
    }

}
