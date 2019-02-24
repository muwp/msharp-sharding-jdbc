package com.msharp.single.jdbc.datasource.config;


import java.io.Serializable;
import java.util.*;

/**
 * Any
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class Any implements Serializable {

    private String m_name;

    private String m_value;

    private Map<String, String> m_attributes;

    private List<Any> m_children;

    public Any() {
    }

    public Any addChild(Any any) {
        this.children(false).add(any);
        return this;
    }

    protected Map<String, String> attributes(boolean readonly) {
        if (this.m_attributes == null) {
            if (readonly) {
                return Collections.emptyMap();
            }

            this.m_attributes = new HashMap();
        }

        return this.m_attributes;
    }

    protected List<Any> children(boolean readonly) {
        if (this.m_children == null) {
            if (readonly) {
                return Collections.emptyList();
            }

            this.m_children = new ArrayList();
        }

        return this.m_children;
    }

    public List<Any> getAllChildren(String name) {
        List<Any> all = new ArrayList();
        Iterator var3 = this.m_children.iterator();

        while (var3.hasNext()) {
            Any child = (Any) var3.next();
            if (child.getName().equals(name)) {
                all.add(child);
            }
        }

        return all;
    }

    public String getAttribute(String name) {
        return (String) this.attributes(true).get(name);
    }

    public Map<String, String> getAttributes() {
        return this.attributes(true);
    }

    public List<Any> getChildren() {
        return this.children(true);
    }

    public Any getFirstChild(String name) {
        Iterator var2 = this.children(true).iterator();
        Any child;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            child = (Any) var2.next();
        } while (!child.getName().equals(name));

        return child;
    }

    public String getName() {
        return this.m_name;
    }

    public String getValue() {
        return this.m_value;
    }

    public boolean hasValue() {
        return this.m_value != null;
    }

    public void mergeAttributes(Any other) {
        this.attributes(false).putAll(other.getAttributes());
    }

    public Any setAttribute(String name, String value) {
        this.attributes(false).put(name, value);
        return this;
    }

    public Any setName(String name) {
        this.m_name = name;
        return this;
    }

    public Any setValue(String value) {
        this.m_value = value;
        return this;
    }

    @Override
    public String toString() {
        return this.m_value != null ? String.format("<%s>%s</%1$s>", this.m_name, this.m_value) : super.toString();
    }
}
