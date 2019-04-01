package ru.ifmo.ctddev.khovanskiy.task3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

public class Implementor {

	private static HashSet<String> imports = new HashSet<String>();

	public static void getUnique(Class<?>[] exceptions) {
		for (int i = 0; i < exceptions.length; ++i) {
			getUnique(exceptions[i]);
		}
	}

	public static void getUnique(Class<?> returnType) {
		if (!returnType.isPrimitive()) {
			if (returnType.isArray()) {
				imports.add(returnType.getComponentType().getCanonicalName());
			} else {
				imports.add(returnType.getCanonicalName());
			}
		}
	}

	public static String superView(Class<?>[] params) {
		if (params.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("super(");
		for (int i = 0; i < params.length - 1; ++i) {
			sb.append("arg" + i + ", ");
		}
		sb.append("arg" + (params.length - 1) + "");
		sb.append(");");
		return sb.toString();
	}

	public static String paramsView(Class<?>[] params) {
		if (params.length == 0) {
			return "()";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (int i = 0; i < params.length - 1; ++i) {
			sb.append(params[i].getSimpleName() + " arg" + i + ", ");
		}
		sb.append(params[params.length - 1].getSimpleName() + " arg"
				+ (params.length - 1) + "");
		sb.append(")");
		return sb.toString();
	}

	public static String exceptionsView(Class<?>[] exceptions) {
		if (exceptions.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("throws ");
		for (int i = 0; i < exceptions.length; ++i) {
			sb.append(exceptions[i].getSimpleName());
		}
		return sb.toString();
	}

	public static void helper(HashMap<String, Method> map, Method[] arr) {
		for (int i = 0; i < arr.length; ++i) {
			int mod = arr[i].getModifiers();
			if (Modifier.isAbstract(mod)
					&& (Modifier.isPublic(mod) || Modifier.isProtected(mod))) {
				String key = arr[i].getName() + " "
						+ paramsView(arr[i].getParameterTypes());
				map.put(key, arr[i]);
			}
		}
	}

	public static Set<Entry<String, Method>> getAllMethods(Class<?> root) {
		HashMap<String, Method> methods = new HashMap<String, Method>();
		Queue<Class<?>> q = new LinkedList<Class<?>>();

		q.add(root);
		while (!q.isEmpty()) {
			Class<?> current = q.poll();

			Console.print(current);
			helper(methods, current.getMethods());
			helper(methods, current.getDeclaredMethods());

			Console.print(current.getSuperclass());
			if (current.getSuperclass() != null) {
				q.add(current.getSuperclass());
			}

			Class<?>[] interfaces = current.getInterfaces();
			for (int i = 0; i < interfaces.length; ++i) {
				Console.print(interfaces[i]);
				q.add(interfaces[i]);
			}
		}

		return methods.entrySet();
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.format("Usage: \n    java %s full.class.name\n",
					Implementor.class.getName());
			return;
		}
		Class<?> c;
		try {
			c = Class.forName(args[0]);
		} catch (Exception e) {
			System.err.println(e.toString());
			return;
		}
		if (Modifier.isFinal(c.getModifiers())) {
			System.err.println("Can not implement a final class "
					+ c.getCanonicalName());
			return;
		}
		if (!c.isInterface() && c.getDeclaredConstructors().length == 0) {
			System.err.println("Super constructor is not visible");
			return;
		}

		Console.print(c.getSimpleName());

		try (BufferedWriter output = new BufferedWriter(new FileWriter(
				c.getSimpleName() + "Impl.java"))) {

			Constructor<?>[] con = c.getDeclaredConstructors();

			Set<Entry<String, Method>> methods = getAllMethods(c);

			imports.add(c.getCanonicalName());

			for (int i = 0; i < con.length; ++i) {
				Console.print(con[i]);
				getUnique(con[i].getParameterTypes());
				getUnique(con[i].getExceptionTypes());
			}

			for (Iterator<Entry<String, Method>> i = methods.iterator(); i
					.hasNext();) {
				Method current = i.next().getValue();
				Console.print(current);
				getUnique(current.getReturnType());
				getUnique(current.getParameterTypes());
				getUnique(current.getExceptionTypes());
			}

			for (Iterator<String> i = imports.iterator(); i.hasNext();) {
				String temp = i.next();
				output.write("import " + temp + ";\n");
			}
			output.write("\n");

			if (c.isInterface()) {
				output.write("public class " + c.getSimpleName()
						+ "Impl implements " + c.getSimpleName() + " {\n");
			} else {
				output.write("public class " + c.getSimpleName()
						+ "Impl extends " + c.getSimpleName() + " {\n");
			}

			for (int i = 0; i < con.length; ++i) {
				if (Modifier.isPublic(con[i].getModifiers())) {
					output.write("\tpublic " + c.getSimpleName() + "Impl "
							+ paramsView(con[i].getParameterTypes()) + " "
							+ exceptionsView(con[i].getExceptionTypes())
							+ " {\n");
				} else if (Modifier.isProtected(con[i].getModifiers())) {
					output.write("\tprotected " + c.getSimpleName() + "Impl "
							+ paramsView(con[i].getParameterTypes()) + " "
							+ exceptionsView(con[i].getExceptionTypes())
							+ " {\n");
				} else {
					continue;
				}
				output.write("\t\t" + superView(con[i].getParameterTypes())
						+ "\n");
				output.write("\t}\n\n");
			}

			for (Iterator<Entry<String, Method>> i = methods.iterator(); i
					.hasNext();) {
				Method current = i.next().getValue();

				if (Modifier.isPublic(current.getModifiers())) {
					output.write("\t@Override\n\tpublic "
							+ current.getReturnType().getSimpleName() + " "
							+ current.getName() + " "
							+ paramsView(current.getParameterTypes()) + " "
							+ exceptionsView(current.getExceptionTypes())
							+ " {\n");
				} else if (Modifier.isProtected(current.getModifiers())) {
					output.write("\t@Override\n\tprotected "
							+ current.getReturnType().getSimpleName() + " "
							+ current.getName() + " "
							+ paramsView(current.getParameterTypes()) + " "
							+ exceptionsView(current.getExceptionTypes())
							+ " {\n");
				} else {
					continue;
				}

				Class<?> returnType = current.getReturnType();
				if (returnType.isPrimitive()) {
					String nameType = returnType.getName();
					if (nameType.equals("void")) {

					} else if (nameType.equals("boolean")) {
						output.write("\t\treturn false;\n");
					} else {
						output.write("\t\treturn 0;\n");
					}
				} else {
					output.write("\t\treturn null;\n");
				}
				output.write("\t}\n\n");
			}
			output.write("}\n");
		}
	}
}
