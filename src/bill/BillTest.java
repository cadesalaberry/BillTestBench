package bill;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Test;

import bill.Bill.BillState;
import bill.Bill.BillStateInHouseOfCommons;
import bill.Bill.BillStateInSenate;

public class BillTest {

	/**
	 * 1. inPreparation test 1
	 */
	@Test
	public void testInPreparationIntroduceInHouse() {

		Bill b = new Bill();

		b.introduceInHouse();
		assertEquals(BillState.inHouseOfCommons, getBillState(b));
		assertEquals(BillStateInHouseOfCommons.firstReading,
				getBillStateInHouseOfCommons(b));
	}

	/**
	 * 1. inPreparation test 2
	 */
	@Test
	public void testInPreparationIntroduceInSenate() {

		Bill b = new Bill();

		b.introduceInSenate();
		assertEquals(BillState.inSenate, getBillState(b));
		assertEquals(BillStateInSenate.firstReadingS, getBillStateInSenate(b));

		assertEquals(false, isCommonBill(b));
	}

	/**
	 * 1. inPreparation test 3, 4, 5, 6 and 7
	 */
	@Test
	public void testInPreparationSneakPaths() {

		Bill b = new Bill();

		BillState expected = BillState.inPreparation;
		assertEquals(expected, getBillState(b));

		b.withdraw();
		assertEquals(expected, getBillState(b));

		b.votePasses();
		assertEquals(expected, getBillState(b));

		b.modify();
		assertEquals(expected, getBillState(b));

		b.voteFails();
		assertEquals(expected, getBillState(b));

		b.royalAssent();
		assertEquals(expected, getBillState(b));
	}

	/**
	 * 2. firstReading test 3
	 */
	@Test
	public void testFirstReadingSwithdraw() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.withdraw();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 2. firstReading test 5
	 */
	@Test
	public void testFirstReadingSvotePasses() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();

		assertEquals(BillState.inSenate, getBillState(b));
		assertEquals(BillStateInSenate.secondReadingS, getBillStateInSenate(b));
	}

	/**
	 * 2. firstReading test 6
	 */
	@Test
	public void testFirstReadingSvoteFails() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.voteFails();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 2. firstReading test 1, 2, 4 and 7
	 */
	@Test
	public void testFirstReadingSsneakPaths() {

		Bill b = new Bill();

		b.introduceInSenate();
		assertEquals(BillState.inSenate, getBillState(b));

		BillStateInSenate expected = BillStateInSenate.firstReadingS;
		assertEquals(expected, getBillStateInSenate(b));

		b.royalAssent();
		assertEquals(expected, getBillStateInSenate(b));

		b.modify();
		assertEquals(expected, getBillStateInSenate(b));

		b.introduceInHouse();
		assertEquals(expected, getBillStateInSenate(b));

		b.introduceInSenate();
		assertEquals(expected, getBillStateInSenate(b));
	}

	/**
	 * 3. secondReadingS test 3
	 */
	@Test
	public void testSecondReadingSwithdraw() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.withdraw();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 3. secondReadingS test 5
	 */
	@Test
	public void testSecondReadingSvotePasses() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();

		assertEquals(BillState.inSenate, getBillState(b));
		assertEquals(BillStateInSenate.committeeConsiderationS,
				getBillStateInSenate(b));
	}

	/**
	 * 3. secondReadingS test 6
	 */
	@Test
	public void testSecondReadingSvoteFails() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.voteFails();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 3. secondReadingS test 1, 2, 4 and 7
	 */
	@Test
	public void testSecondReadingSsneakPaths() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();

		BillStateInSenate expected = BillStateInSenate.secondReadingS;
		assertEquals(expected, getBillStateInSenate(b));

		b.royalAssent();
		assertEquals(expected, getBillStateInSenate(b));

		b.modify();
		assertEquals(expected, getBillStateInSenate(b));

		b.introduceInHouse();
		assertEquals(expected, getBillStateInSenate(b));

		b.introduceInSenate();
		assertEquals(expected, getBillStateInSenate(b));
	}

	/**
	 * Uses reflection to avoid modifying the code. Gets the bill state.
	 * 
	 * @param b
	 * @return null if not found
	 */
	private BillState getBillState(Bill b) {

		try {

			Field f;
			f = b.getClass().getDeclaredField("billState");
			f.setAccessible(true);
			return (BillState) f.get(b);

		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}

	private BillStateInHouseOfCommons getBillStateInHouseOfCommons(Bill b) {

		try {

			Field f;
			f = b.getClass().getDeclaredField("billStateInHouseOfCommons");
			f.setAccessible(true);
			return (BillStateInHouseOfCommons) f.get(b);

		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}

	private BillStateInSenate getBillStateInSenate(Bill b) {

		try {

			Field f;
			f = b.getClass().getDeclaredField("billStateInSenate");
			f.setAccessible(true);
			return (BillStateInSenate) f.get(b);

		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}

	private Boolean isCommonBill(Bill b) {

		try {

			Field f;
			f = b.getClass().getDeclaredField("isCommonsBill");
			f.setAccessible(true);
			return (Boolean) f.get(b);

		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

}
