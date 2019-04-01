package ru.ifmo.ctddev.khovanskiy.task9;

public class LocalPerson implements ILocalPerson {

	private static final long serialVersionUID = -4261230460687588822L;
	private final String passwordId;
	private final String name;
	private final String surname;

	public LocalPerson(String passwordNumber, String name, String surname) {
		this.passwordId = passwordNumber;
		this.name = name;
		this.surname = surname;
	}

	@Override
	public String getPassportId() {
		return passwordId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSurname() {
		return surname;
	}
}
