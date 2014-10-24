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
		b.votePasses();
		b.setIsCommonsBill(true);
		
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
		b.votePasses();
		b.setIsCommonsBill(false);
		
		assertEquals(BillState.inHouseOfCommons, getBillState(b));
		assertEquals(BillStateInHouseOfCommons.firstReading, getBillStateInHouseOfCommons(b));
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
		assertEquals(BillStateInHouseOfCommons.secondReading, getBillStateInHouseOfCommons(b));
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
	public void testsecondReadingwithdraw() {

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
	public void testsecondReadingvotePasses() {

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
	public void testsecondReadingvoteFails() {

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
	public void testsecondReadingsneakPaths() {

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
	public void testcommitteeConsiderationvotePasses() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();
		b.votePasses();

		assertEquals(BillState.inHouseOfCommons, getBillState(b));
		assertEquals(BillStateInHouseOfCommons.thirdReading, getBillStateInHouseOfCommons(b));
	}

	/**
	 * 11. committeeConsideration test 6
	 */
	@Test
	public void testcommitteeConsiderationvoteFails() {

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
	public void testcommitteeConsiderationsneakPaths() {

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
	public void testthirdReadingwithdraw() {

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
	public void testthirdReadingvotePassesIsCommonBill() {

		Bill b = new Bill();

		b.introduceInHouse();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.votePasses();
		b.setIsCommonsBill(true);

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
		b.votePasses();
		b.setIsCommonsBill(false);
		
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
