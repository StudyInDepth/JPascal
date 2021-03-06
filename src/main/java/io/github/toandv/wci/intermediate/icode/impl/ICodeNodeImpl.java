
package io.github.toandv.wci.intermediate.icode.impl;

import io.github.toandv.wci.intermediate.icode.ICodeKey;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.ICodeNodeType;

import java.util.*;

public class ICodeNodeImpl implements ICodeNode {

    private ICodeNodeType type;

    private ICodeNode parent;

    private List<ICodeNode> children;

    private Map<ICodeKey, Object> attributeMap;

    public ICodeNodeImpl(ICodeNodeType type) {
        this.type = type;
        this.parent = null;
        this.children = new ArrayList<>(2);
        this.attributeMap = new HashMap<>(2);
    }

    @Override
    public ICodeNode getParent() {
        return this.parent;
    }

    @Override
    public ICodeNodeType getType() {
        return this.type;
    }

    @Override
    public ICodeNode addChild(ICodeNode child) {
        if (child == null) {
            return null;
        }
        this.children.add(child);
        if (child instanceof ICodeNodeImpl) {
            ((ICodeNodeImpl) child).parent = this;
        }
        return child;
    }

    @Override
    public Object getAttribute(ICodeKey key) {
        return this.attributeMap.get(key);
    }

    @Override
    public Object setAttribute(ICodeKey key, Object value) {
        this.attributeMap.put(key, value);
        return value;
    }

    @Override
    public Object setMultiValuesAttribute(ICodeKey key, Object value) {
        Set<Object> values = (Set<Object>) this.attributeMap.get(key);
        if (values == null) {
            values = new HashSet<>();
        }
        values.add(value);
        this.attributeMap.putIfAbsent(key, values);
        return value;
    }

    @Override
    public ICodeNode copy() {
        ICodeNodeImpl node = new ICodeNodeImpl(this.type);
        // copy attributes
        node.attributeMap.putAll(this.attributeMap);
        // TODO
        // copy children
        // node.children.addAll(this.children);
        // copy parent
        // node.parent = this.parent;
        return node;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }

    @Override
    public List<ICodeNode> getChildren() {
        return children;
    }

    public Map<ICodeKey, Object> getAttributeMap() {
        return attributeMap;
    }

}
