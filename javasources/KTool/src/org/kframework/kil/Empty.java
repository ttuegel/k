package org.kframework.kil;

import org.kframework.kil.loader.Constants;
import org.kframework.kil.visitors.Modifier;
import org.kframework.kil.visitors.Transformer;
import org.kframework.kil.visitors.Visitor;
import org.kframework.kil.visitors.exceptions.TransformerException;
import org.w3c.dom.Element;

public class Empty extends Term {

	public Empty(String sort) {
		super(sort);
	}

	public Empty(String location, String filename, String sort) {
		super(location, filename, sort);
	}

	public Empty(Element element) {
		super(element);
		this.sort = element.getAttribute(Constants.SORT_sort_ATTR);
	}

	public Empty(Empty empty) {
		super(empty);
	}

	public String toString() {
		return "." + sort + " ";
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public void applyToAll(Modifier visitor) {
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	@Override
	public ASTNode accept(Transformer visitor) throws TransformerException {
		return visitor.transform(this);
	}

	@Override
	public Empty shallowCopy() {
		return new Empty(this);
	}
}
