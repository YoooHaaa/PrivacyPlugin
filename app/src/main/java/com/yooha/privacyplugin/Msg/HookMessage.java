package com.yooha.privacyplugin.Msg;


import com.yooha.privacyplugin.Common.Data;
import java.util.List;

public class HookMessage {
    public String mStackTrace = null;
    public String mClass = null;
    public String mMethod = null;
    public String mMethodSignature = null;
    public int mLevel = Data.LEVEL_GENERAL;
    public List<String> mParams = null;
    public String mReturn = "";
    public String mDescription = null;
    public int mCount = 0;

    public HookMessage(String stacktrace, String cls, String method, String sig, int level, List params, String ret, String des, int count){
        mStackTrace = stacktrace;
        mClass = cls;
        mMethod = method;
        mMethodSignature = sig;
        mLevel = level;
        mParams = params;
        mReturn = ret;
        mDescription = des;
        mCount = count;
    }

    public static class Builder{
        public String mStackTrace = "";
        public String mClass = "";
        public String mMethod = "";
        public String mMethodSignature = "";
        public int mLevel = Data.LEVEL_GENERAL;
        public List<String> mParams = null;
        public String mReturn = "";
        public String mDescription = "";
        public int mCount = 0;

        public Builder(){
        }

        public Builder setCount(int c){
            this.mCount = c;
            return this;
        }

        public Builder setParams(List ls){
            this.mParams = ls;
            return this;
        }

        public Builder setReturn(String s){
            this.mReturn = s;
            return this;
        }

        public Builder setStackTrace(String s){
            this.mStackTrace = s;
            return this;
        }

        public Builder setClass(String s){
            this.mClass = s;
            return this;
        }

        public Builder setMethod(String s){
            this.mMethod = s;
            return this;
        }

        public Builder setMethodSignature(String s){
            this.mMethodSignature = s;
            return this;
        }

        public Builder setLevel(int level){
            this.mLevel = level;
            return this;
        }

        public Builder setDescription(String s){
            this.mDescription = s;
            return this;
        }

        public HookMessage build(){
            return new HookMessage(mStackTrace, mClass, mMethod, mMethodSignature, mLevel, mParams, mReturn, mDescription, mCount);
        }
    }


}
