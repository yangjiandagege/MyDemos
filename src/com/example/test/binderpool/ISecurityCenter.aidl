package com.example.test.binderpool;

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}