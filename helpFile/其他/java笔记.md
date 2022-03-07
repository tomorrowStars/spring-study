# 1,  java 删除字符串左边空格和右边空格 trimLeft trimRight

```java

 
/**
     * 去右空格
     * @param str
     * @return
     */
    public String trimRight(String str) {
        if (str == null || str.equals("")) {
            return str;
        } else {
            return str.replaceAll("[　 ]+$", "");
        }
    }

    /**
     * 去左空格
     * @param str
     * @return
     */
    public String trimLeft(String str) {
        if (str == null || str.equals("")) {
            return str;
        } else {
            return str.replaceAll("^[　 ]+", "");
        }
    }
```

