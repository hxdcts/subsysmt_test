package com.pfb.common;

public enum TradeTypeEnum {

	SCAN{
        @Override
        public String getText() {
            return "扫码";
        }
        @Override
        public String getValue() {
            return "SCAN";
        }
    },SIGN{
        @Override
        public String getText() {
            return "签名";
        }
        @Override
        public String getValue() {
            return "SIGN";
        }
    };
    public abstract String getText();
    public abstract String getValue();
}
