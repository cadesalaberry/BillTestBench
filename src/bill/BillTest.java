package bill;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
	 * 4. committeeConsiderationS test 5
	 */
	@Test
	public void testCommitteeConsiderationSvotePasses() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();
		b.votePasses();

		assertEquals(BillState.inSenate, getBillState(b));
		assertEquals(BillStateInSenate.thirdReadingS, getBillStateInSenate(b));
	}

	/**
	 * 4. committeeConsiderationS test 6
	 */
	@Test
	public void testCommitteeConsiderationSvoteFails() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();
		b.voteFails();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 4. committeeConsiderationS test 1, 2, 4 and 7
	 */
	@Test
	public void testCommitteeConsiderationSsneakPaths() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();

		BillStateInSenate expected = BillStateInSenate.committeeConsiderationS;
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
	 * 5. thirdReadingS test 3
	 */
	@Test
	public void testThirdReadingSwithdraw() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.withdraw();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 5. thirdReadingS test 5
	 */
	@Test
	public void testThirdReadingSvotePassesIsCommonBill() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.setIsCommonsBill(true);
		b.votePasses();

		assertEquals(BillState.awaitingRoyalAssent, getBillState(b));
	}

	/**
	 * 5. thirdReadingS test 6
	 */
	@Test
	public void testThirdReadingSvotePassesIsNOTCommonBill() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.setIsCommonsBill(false);
		b.votePasses();

		assertEquals(BillState.inHouseOfCommons, getBillState(b));
		assertEquals(BillStateInHouseOfCommons.firstReading,
				getBillStateInHouseOfCommons(b));
	}

	/**
	 * 5. thirdReadingS test 7
	 */
	@Test
	public void testThirdReadingSvoteFails() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.voteFails();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 5. thirdReadingS test 1, 2, 4 and 8
	 */
	@Test
	public void testThirdReadingSsneakPaths() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();
		b.votePasses();

		BillStateInSenate expected = BillStateInSenate.thirdReadingS;
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
	 * 6. AwaitingRoyalAssent test 7
	 */
	@Test
	public void testAwaitingRoyalAssentRoyalAssent() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.royalAssent();

		assertEquals(BillState.actOfParliament, getBillState(b));
	}

	/**
	 * 6. awaitingRoyalAssent test 1, 2, 4 and 8
	 */
	@Test
	public void testAwaitingRoyalAssentSneakPaths() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.votePasses();

		BillState expected = BillState.awaitingRoyalAssent;
		assertEquals(expected, getBillState(b));

		b.withdraw();
		assertEquals(expected, getBillState(b));

		b.modify();
		assertEquals(expected, getBillState(b));

		b.introduceInHouse();
		assertEquals(expected, getBillState(b));

		b.introduceInSenate();
		assertEquals(expected, getBillState(b));

		b.votePasses();
		assertEquals(expected, getBillState(b));

		b.voteFails();
		assertEquals(expected, getBillState(b));
	}

	/**
	 * 7. awaitingRoyalAssent test 1, 2, 3, 4, 5, 6 and 7
	 */
	@Test
	public void testActOfParliamentSneakPaths() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.royalAssent();

		BillState expected = BillState.actOfParliament;
		assertEquals(expected, getBillState(b));

		b.royalAssent();
		assertEquals(expected, getBillState(b));

		b.withdraw();
		assertEquals(expected, getBillState(b));

		b.modify();
		assertEquals(expected, getBillState(b));

		b.introduceInHouse();
		assertEquals(expected, getBillState(b));

		b.introduceInSenate();
		assertEquals(expected, getBillState(b));

		b.votePasses();
		assertEquals(expected, getBillState(b));

		b.voteFails();
		assertEquals(expected, getBillState(b));
	}

	/**
	 * 8. withdrawn test 4
	 */
	@Test
	public void testWithdrawnModify() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.voteFails();
		b.modify();

		BillState expected = BillState.inPreparation;
		assertEquals(expected, getBillState(b));
	}

	/**
	 * 8. withdrawn test 1, 2, 3, 5, 6 and 7
	 */
	@Test
	public void testWithdrawnSneakPaths() {

		Bill b = new Bill();

		b.introduceInSenate();
		b.voteFails();

		BillState expected = BillState.withdrawn;
		assertEquals(expected, getBillState(b));

		b.royalAssent();
		assertEquals(expected, getBillState(b));

		b.withdraw();
		assertEquals(expected, getBillState(b));

		b.introduceInHouse();
		assertEquals(expected, getBillState(b));

		b.introduceInSenate();
		assertEquals(expected, getBillState(b));

		b.votePasses();
		assertEquals(expected, getBillState(b));

		b.voteFails();
		assertEquals(expected, getBillState(b));
	}

	/**
	 * 9. firstReading test 3
	 */
	@Test
	public void testFirstReadingwithdraw() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.withdraw();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 9. firstReading test 5
	 */
	@Test
	public void testFirstReadingvotePasses() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();

		assertEquals(BillState.inHouseOfCommons, getBillState(b));
		assertEquals(BillStateInHouseOfCommons.secondReading,
				getBillStateInHouseOfCommons(b));
	}

	/**
	 * 9. firstReading test 6
	 */
	@Test
	public void testFirstReadingvoteFails() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.voteFails();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 9. firstReading test 1, 2, 4 and 7
	 */
	@Test
	public void testFirstReadingsneakPaths() {

		Bill b = new Bill();

		b.introduceInHouse();
		assertEquals(BillState.inHouseOfCommons, getBillState(b));

		BillStateInHouseOfCommons expected = BillStateInHouseOfCommons.firstReading;
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.royalAssent();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.modify();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.introduceInHouse();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.introduceInSenate();
		assertEquals(expected, getBillStateInHouseOfCommons(b));
	}

	/**
	 * 10. secondReading test 3
	 */
	@Test
	public void testSecondReadingwithdraw() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.withdraw();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 10. secondReading test 5
	 */
	@Test
	public void testSecondReadingvotePasses() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();

		assertEquals(BillState.inHouseOfCommons, getBillState(b));
		assertEquals(BillStateInHouseOfCommons.committeeConsideration,
				getBillStateInHouseOfCommons(b));
	}

	/**
	 * 10. secondReading test 6
	 */
	@Test
	public void testSecondReadingvoteFails() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.voteFails();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 10. secondReading test 1, 2, 4 and 7
	 */
	@Test
	public void testSecondReadingsneakPaths() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();

		BillStateInHouseOfCommons expected = BillStateInHouseOfCommons.secondReading;
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.royalAssent();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.modify();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.introduceInHouse();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.introduceInSenate();
		assertEquals(expected, getBillStateInHouseOfCommons(b));
	}

	/**
	 * 11. committeeConsideration test 5
	 */
	@Test
	public void testCommitteeConsiderationvotePasses() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();
		b.votePasses();

		assertEquals(BillState.inHouseOfCommons, getBillState(b));
		assertEquals(BillStateInHouseOfCommons.thirdReading,
				getBillStateInHouseOfCommons(b));
	}

	/**
	 * 11. committeeConsideration test 6
	 */
	@Test
	public void testCommitteeConsiderationvoteFails() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();
		b.voteFails();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 11. committeeConsideration test 1, 2, 4 and 7
	 */
	@Test
	public void testCommitteeConsiderationsneakPaths() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();

		BillStateInHouseOfCommons expected = BillStateInHouseOfCommons.committeeConsideration;
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.royalAssent();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.modify();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.introduceInHouse();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.introduceInSenate();
		assertEquals(expected, getBillStateInHouseOfCommons(b));
	}

	/**
	 * 12. thirdReading test 3
	 */
	@Test
	public void testThirdReadingwithdraw() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.withdraw();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 12. thirdReading test 5
	 */
	@Test
	public void testThirdReadingvotePassesIsCommonBill() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.setIsCommonsBill(true);
		b.votePasses();

		assertEquals(BillState.inSenate, getBillState(b));
		assertEquals(BillStateInSenate.firstReadingS, getBillStateInSenate(b));

	}

	/**
	 * 12. thirdReading test 6
	 */
	@Test
	public void testThirdReadingvotePassesIsNOTCommonBill() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.setIsCommonsBill(false);
		b.votePasses();

		assertEquals(BillState.awaitingRoyalAssent, getBillState(b));
	}

	/**
	 * 12. thirdReading test 7
	 */
	@Test
	public void testThirdReadingvoteFails() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.voteFails();

		assertEquals(BillState.withdrawn, getBillState(b));
	}

	/**
	 * 12. thirdReading test 1, 2, 4 and 8
	 */
	@Test
	public void testThirdReadingsneakPaths() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();
		b.votePasses();

		BillStateInHouseOfCommons expected = BillStateInHouseOfCommons.thirdReading;
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.royalAssent();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.modify();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.introduceInHouse();
		assertEquals(expected, getBillStateInHouseOfCommons(b));

		b.introduceInSenate();
		assertEquals(expected, getBillStateInHouseOfCommons(b));
	}

	/**
	 * 13. missing simple code coverage.
	 */
	@Test
	public void missingSimpleCodeCoverage() {

		Bill b = new Bill();
		b.introduceInSenate();
		b.voteDenied();

		b = new Bill();
		b.introduceInHouse();
		b.voteDenied();

		b = new Bill();
		// Dummy methods.
		b.toString();
		b.delete();
		b.isIsCommonsBill();
	}

	/**
	 * 14. missing enum code coverage.
	 */
	@Test
	public void missingEnumCodeCoverage() {

		BillState.valueOf("withdrawn");
		BillState.values();
		BillStateInHouseOfCommons.valueOf("firstReading");
		BillStateInHouseOfCommons.values();
		BillStateInSenate.valueOf("firstReadingS");
		BillStateInSenate.values();
	}

	/**
	 * 15. missing code coverage in votePasses.
	 */
	@Test
	public void missingVotePassesCodeCoverage() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.setIsCommonsBill(false);
		b.votePasses();
		
		b = new Bill();
		b.introduceInSenate();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.setIsCommonsBill(false);
		b.votePasses();
	}
	
	/**
	 * 16. missing code coverage in votePasses.
	 */
	@Test
	public void missingCommitteeConsiderationExitCodeCoverage() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();
		b.withdraw();
	}

	/**
	 * Uses reflection to avoid modifying the code. Gets the bill state.
	 * 
	 * @param b
	 * @return null if not found
	 */
	private BillState getBillState(Bill b) {
		return (BillState) getField(b, "billState");
	}

	/**
	 * Uses reflection to avoid modifying the code. Sets the bill state.
	 * 
	 * @param b
	 * @return null if not found
	 */
	private void setBillState(Bill b, BillState s) {

		try {

			Field f;
			f = b.getClass().getDeclaredField("billState");
			f.setAccessible(true);
			f.set(b, s);

		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Uses reflection to avoid modifying the code. Sets the bill state.
	 * 
	 * @param b
	 * @return null if not found
	 */
	private void setBillStateInHOC(Bill b, BillState s) {

		try {

			Method m;
			m = b.getClass().getDeclaredMethod("billState");
			m.setAccessible(true);
			m.invoke(b, s);

		} catch (IllegalAccessException | IllegalArgumentException
				| SecurityException | NoSuchMethodException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private BillStateInHouseOfCommons getBillStateInHouseOfCommons(Bill b) {
		return (BillStateInHouseOfCommons) getField(b,
				"billStateInHouseOfCommons");
	}

	private BillStateInSenate getBillStateInSenate(Bill b) {
		return (BillStateInSenate) getField(b, "billStateInSenate");
	}

	private Boolean isCommonBill(Bill b) {
		return (Boolean) getField(b, "isCommonsBill");
	}

	private Object getField(Object o, String name) {
		try {

			Field f;
			f = o.getClass().getDeclaredField(name);
			f.setAccessible(true);
			return f.get(o);

		} catch (IllegalAccessException | IllegalArgumentException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}
}
