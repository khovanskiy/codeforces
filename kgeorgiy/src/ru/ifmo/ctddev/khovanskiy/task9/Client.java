package ru.ifmo.ctddev.khovanskiy.task9;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException {
		if (args.length < 5) {
			System.out
					.println("Usage: name surname passportNumber accountNumber amount");
			System.exit(0);
		}
		String name = args[0];
		String surname = args[1];
		String passportNumber = args[2];
		String accountNumber = args[3];
		int amount = Integer.parseInt(args[4]);

		IBank bank = (IBank) Naming.lookup("bank");
		IPerson person = bank.getRemotePerson(passportNumber);
		if (person == null) {
			bank.createPerson(passportNumber, name, surname);
			person = bank.getRemotePerson(passportNumber);

			Console.print("Created person " + passportNumber);
		} else {
			Console.print("Exists person " + person.getPassportId());
		}

		if (person.getName().equals(name)
				&& person.getSurname().equals(surname)) {
			IAccount account = bank.getAccount(accountNumber);
			if (account == null) {
				bank.createAccount(accountNumber, passportNumber);
				account = bank.getAccount(accountNumber);
				Console.print("Account [" + account.getId()
						+ "] has been created");
			} else {
				Console.print("Account [" + account.getId() + "] = "
						+ account.getAmount());
			}
			if (account.getOwner().equals(passportNumber)) {
				account.changeAmount(amount);
				Console.print("Updated Account [" + account.getId() + "] = "
						+ account.getAmount());
			} else {
				Console.print("False account data");
			}
		}
	}
}
