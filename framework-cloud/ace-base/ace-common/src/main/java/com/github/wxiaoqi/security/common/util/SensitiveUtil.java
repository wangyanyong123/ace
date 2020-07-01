package com.github.wxiaoqi.security.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SensitiveUtil {

    private static boolean flag;
    private static String senWord;
    private static List<wordTreeNode> tree = null;
    private static String sensitiveWordStr = null;


    /**
     * 判断文本中是否有敏感词
     * @param content
     * @return
     */
    public static ArrayList<String> checkWords(String content){
//		String cacheWords = (String)OcsUtil.get("cacheWords");;
        //但敏感词为空或者ESB的配置有改动时重新加载
//		if(StringUtils.isEmpty(sensitiveWordStr) || StringUtils.isEmpty(cacheWords)){
//			String sensitiveWordStrTemp = "sql";
//			init(sensitiveWordStrTemp);
//			OcsUtil.set(cacheWords, 30*60, sensitiveWordStrTemp);
//		}
        ArrayList<String> senWordList = new ArrayList<>();;
        if(tree!=null && content!=null){
            content = replaceSpecialChars(content);
            wordTreeNode rootNode = null;
            for(int i = 0;i < content.length();i++){
                for(wordTreeNode node:tree){
                    if(node.letter == content.charAt(i)){
                        rootNode = node;
                        break;
                    }
                }
                if(rootNode != null){
                    senWord = "";
                    if(checkLetter(content, rootNode, i)){
                        senWordList.add(senWord);
                        senWord = "";
                    }
                    else{
                        rootNode = null;
                    }
                }
            }
        }
        return senWordList;
    }

    /**
     * 从文本当前位置字符开始，判断是否属于这颗树（看是否可到达某个isEnd为true的节点，可达，说明有敏感词）
     * @param content
     * @param node
     * @param index
     * @return
     */
    private static boolean checkLetter(String content,wordTreeNode node,int index){
        if(content.charAt(index) == node.letter){
            if(node.isEnd){
                senWord+=node.letter;
                return true;
            }
            else{
                if(index == content.length() - 1){
                    return false;
                }
                for(wordTreeNode childNode:node.children){
                    if(checkLetter(content, childNode, index+1)){
                        senWord+=node.letter;
                        return true;
                    }
                }
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * 去除字符串中的特殊字符(包括标点符号)
     * @param resource
     * @return
     */
    public static String replaceSpecialChars(String resource){
        String result = "";
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(resource);
        result = m.replaceAll("").trim();
        result = result.replace("\\", "");
        return result;
    }

    public static void init(String sensitiveWordStrTemp){
        sensitiveWordStr = sensitiveWordStrTemp;
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(sensitiveWordStr)){
            String[] array = sensitiveWordStr.split(",");
            if(array!=null && array.length>0){
                List<SensitiveWord> sensitiveWords = new ArrayList<SensitiveWord>();
                for (int i = 0; i < array.length; i++) {
                    if(StringUtils.isNotEmpty(array[i])){
                        sensitiveWords.add(new SensitiveWord(UUIDUtils.generateUuid(),array[i]));
                    }
                }
                if(tree != null){
                    tree = null;
                }
                tree = SensitiveUtil.buildWordsTrees(sensitiveWords);
            }
        }
    }

    /**
     * 把数据库中的敏感词建立成敏感词树
     * @param words
     */
    private static List<wordTreeNode> buildWordsTrees(List<SensitiveWord> words){
        List<wordTreeNode> rootList = new ArrayList<>();
        for(SensitiveWord word:words){
            wordTreeNode newRoot = null;
            for(wordTreeNode root:rootList){
                if(root.letter == word.getWords().charAt(0)){
                    newRoot = root;
                    break;
                }
            }
            if(newRoot == null){
                newRoot = new wordTreeNode(word.getWords().charAt(0), false);
                rootList.add(newRoot);
            }
            buildOneTree(newRoot, word.getWords(),0);
            flag = false;
        }
        return rootList;
    }

    /**
     * 建一颗敏感词树
     * @param node
     * @param word
     * @param index
     */
    private static void buildOneTree(wordTreeNode node,String word,int index){
        if(flag){
            return;
        }
        if(node.letter==word.charAt(index)){
            if(index == word.length()-1){
                flag = true;
                node.isEnd = true;
                return;
            }
            if(node.children == null){
                wordTreeNode newNode = new wordTreeNode(word.charAt(index+1), false);
                newNode.parent = node;
                node.children = new ArrayList<>();
                node.children.add(newNode);
                if(index+1<word.length()-1?false:true){
                    flag = true;
                    newNode.isEnd = true;
                    return;
                }
                else{
                    buildOneTree(newNode, word, (index+1));
                }
            }
            else{
                for(wordTreeNode childNode:node.children){
                    buildOneTree(childNode, word, index+1);
                    if(flag){
                        return;
                    }
                }
            }
        }
        else{
            if(node.letter == node.parent.children.get(node.parent.children.size() - 1).letter){//判断是不是父节点的最后一个子节点，如果是，创建一个新节点；不是，return
                wordTreeNode newNode = new wordTreeNode(word.charAt(index), false);
                newNode.parent = node.parent;
                node.parent.children.add(newNode);
                if(index == word.length()-1){
                    flag = true;
                    newNode.isEnd = true;
                    return;
                }
                else{
                    buildOneTree(newNode, word, index);
                }
            }
            else{
                return;
            }
        }
    }


    public static class SensitiveWord {
        private String id;

        private String words;

        public SensitiveWord(String id,String words){
            this.id=id;
            this.words=words;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id == null ? null : id.trim();
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words == null ? null : words.trim();
        }
    }






    /**
     * 敏感词节点
     * @author Administrator
     *
     */
    public static class wordTreeNode implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 2064395920839083734L;

        public wordTreeNode parent;

        public char letter;

        public boolean isEnd;

        public ArrayList<wordTreeNode> children;

        public wordTreeNode(char word,boolean isEnd){
            this.letter = word;
            this.isEnd = isEnd;
        }
    }

    public static List<wordTreeNode> getTree() {
        return tree;
    }

    public static void setTree(List<wordTreeNode> tree) {
        SensitiveUtil.tree = tree;
    }




























}
