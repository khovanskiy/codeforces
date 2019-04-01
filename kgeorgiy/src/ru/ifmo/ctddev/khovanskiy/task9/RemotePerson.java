package ru.ifmo.ctddev.khovanskiy.task9;

public class RemotePerson implements IRemotePerson {
	protected String passwordId;
	protected String name;
	protected String surname;

	public RemotePerson(String passwordNumber, String name, String surname) {
		this.passwordId = passwordNumber;
		this.name = name;
		this.surname = surname;
	}

	@Override
	public synchronized String getPassportId() {
		return passwordId;
	}

	@Override
	public synchronized String getName() {
		return name;
	}

	@Override
	public synchronized String getSurname() {
		return surname;
	}
}
