/*
+---------------------------------------------------------------------------+
| OpenX v${RELEASE_MAJOR_MINOR}                                                                |
| ======${RELEASE_MAJOR_MINOR_DOUBLE_UNDERLINE}                                                                 |
|                                                                           |
| Copyright (c) 2003-2009 OpenX Limited                                     |
| For contact details, see: http://www.openx.org/                           |
|                                                                           |
| This program is free software; you can redistribute it and/or modify      |
| it under the terms of the GNU General Public License as published by      |
| the Free Software Foundation; either version 2 of the License, or         |
| (at your option) any later version.                                       |
|                                                                           |
| This program is distributed in the hope that it will be useful,           |
| but WITHOUT ANY WARRANTY; without even the implied warranty of            |
| MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             |
| GNU General Public License for more details.                              |
|                                                                           |
| You should have received a copy of the GNU General Public License         |
| along with this program; if not, write to the Free Software               |
| Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA |
+---------------------------------------------------------------------------+
$Id$
*/

package org.openx.advertiser;

import java.net.MalformedURLException;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.openx.utils.DateUtils;
import org.openx.utils.ErrorMessage;
import org.openx.utils.TextUtils;

/**
 * Verify Advertiser Zone Statistics method
 *
 * @author     Andriy Petlyovanyy <apetlyovanyy@lohika.com>
 */
public class TestAdvertiserZoneStatistics extends AdvertiserTestCase {
	private Integer advertiserId;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		advertiserId = createAdvertiser();
	}

	@Override
	protected void tearDown() throws Exception {
		deleteAdvertiser(advertiserId);
		super.tearDown();
	}

	/**
	 * Execute test method with error
	 *
	 * @param params -
	 *            parameters for test method
	 * @param errorMsg -
	 *            true error messages
	 * @throws MalformedURLException
	 */
	private void executeAdvertiserZoneStatisticsWithError(Object[] params,
			String errorMsg) throws MalformedURLException {
		try {
			execute(ADVERTISER_ZONE_STATISTICS_METHOD, params);
			fail(ErrorMessage.METHOD_EXECUTED_SUCCESSFULLY_BUT_SHOULD_NOT_HAVE);
		} catch (XmlRpcException e) {
			assertEquals(ErrorMessage.WRONG_ERROR_MESSAGE, errorMsg, e
					.getMessage());
		}
	}

	/**
	 * Test method with all required fields and some optional.
	 *
	 * @throws XmlRpcException
	 */
	@SuppressWarnings("unchecked")
	public void testAdvertiserZoneStatisticsAllReqAndSomeOptionalFields()
			throws XmlRpcException {
		assertNotNull("Can't get advertiserId by setUp method.", advertiserId);

		Object[] params = new Object[] { sessionId, advertiserId,
				DateUtils.MIN_DATE_VALUE };
		final Object[] result = (Object[]) client.execute(
				ADVERTISER_ZONE_STATISTICS_METHOD, params);

		assertNotNull("Can't get result for advertiserBannerStatisticsMethod.",
				result);

		if (result.length > 0) {
			final Map<String, Object> item = (Map<String, Object>) result[0];
			assertTrue("Can't find 'publisherId' field.", item
					.containsKey("publisherId"));
			assertTrue("Can't find 'publisherName' field.", item
					.containsKey("publisherName"));
			assertTrue("Can't find 'zoneId' field.", item.containsKey("zoneId"));
			assertTrue("Can't find 'zoneName' field.", item
					.containsKey("zoneName"));
			assertTrue("Can't find 'requests' field.", item
					.containsKey("requests"));
			assertTrue("Can't find 'impressions' field.", item
					.containsKey("impressions"));
			assertTrue("Can't find 'clicks' field.", item.containsKey("clicks"));
			assertTrue("Can't find 'revenue' field.", item
					.containsKey("revenue"));
		}
	}

	/**
	 * Test method without some required fields.
	 *
	 * @throws Exception
	 */
	public void testAdvertiserZoneStatisticsWithoutSomeRequiredFields()
			throws Exception {
		Object[] params = new Object[] { sessionId };

		executeAdvertiserZoneStatisticsWithError(params, ErrorMessage
					.getMessage(ErrorMessage.INCORRECT_PARAMETERS_PASSED_TO_METHOD,
							"5, 4, 3, or 2", "1"));
	}

	/**
	 * Test method with fields that has value greater than max.
	 *
	 * @throws MalformedURLException
	 * @throws XmlRpcException
	 */
	public void testAdvertiserZoneStatisticsGreaterThanMaxFieldValueError()
			throws MalformedURLException, XmlRpcException {
		Object[] params = new Object[] { sessionId, advertiserId,
				DateUtils.MIN_DATE_VALUE, DateUtils.DATE_GREATER_THAN_MAX };

		try {
			client.execute(ADVERTISER_ZONE_STATISTICS_METHOD, params);
			fail(ErrorMessage.METHOD_EXECUTED_SUCCESSFULLY_BUT_SHOULD_NOT_HAVE);
		} catch (XmlRpcException e) {
			assertEquals(ErrorMessage.YEAR_SHOULD_BE_IN_RANGE_1970_2038, e
					.getMessage());
		}
	}

	/**
	 * Test method with fields that has value less than min
	 *
	 * @throws MalformedURLException
	 */
	public void testAdvertiserZoneStatisticsLessThanMinFieldValueError()
			throws MalformedURLException {
		Object[] params = new Object[] { sessionId, advertiserId,
				DateUtils.DATE_LESS_THAN_MIN, DateUtils.MAX_DATE_VALUE };

		try {
			client.execute(ADVERTISER_ZONE_STATISTICS_METHOD, params);
			fail(ErrorMessage.METHOD_EXECUTED_SUCCESSFULLY_BUT_SHOULD_NOT_HAVE);
		} catch (XmlRpcException e) {
			assertEquals(ErrorMessage.YEAR_SHOULD_BE_IN_RANGE_1970_2038, e
					.getMessage());
		}
	}

	/**
	 * Test method with fields that has min. allowed values.
	 *
	 * @throws XmlRpcException
	 * @throws MalformedURLException
	 */
	public void testAdvertiserZoneStatisticsMinValues() throws XmlRpcException,
			MalformedURLException {
		Object[] params = new Object[] { sessionId, advertiserId,
				DateUtils.MIN_DATE_VALUE, DateUtils.MIN_DATE_VALUE };

		client.execute(ADVERTISER_ZONE_STATISTICS_METHOD, params);
	}

	/**
	 * Test method with fields that has max. allowed values.
	 *
	 * @throws XmlRpcException
	 * @throws MalformedURLException
	 */
	public void testAdvertiserZoneStatisticsMaxValues() throws XmlRpcException,
			MalformedURLException {
		Object[] params = new Object[] { sessionId, advertiserId,
				DateUtils.MAX_DATE_VALUE, DateUtils.MAX_DATE_VALUE };

		client.execute(ADVERTISER_ZONE_STATISTICS_METHOD, params);
	}

	/**
	 * AdvertiserZoneStatistics advertiser with unknown id
	 *
	 * @throws XmlRpcException
	 * @throws MalformedURLException
	 */
	public void testAdvertiserZoneStatisticsUnknownIdError()
			throws XmlRpcException, MalformedURLException {
		final Integer id = createAdvertiser();
		deleteAdvertiser(id);
		Object[] params = new Object[] { sessionId, id };

		try {
			client.execute(ADVERTISER_ZONE_STATISTICS_METHOD, params);
			fail(ErrorMessage.METHOD_EXECUTED_SUCCESSFULLY_BUT_SHOULD_NOT_HAVE);
		} catch (XmlRpcException e) {
			assertEquals(ErrorMessage.WRONG_ERROR_MESSAGE, ErrorMessage
					.getMessage(ErrorMessage.UNKNOWN_ID_ERROR, ADVERTISER_ID),
					e.getMessage());
		}
	}

	/**
	 * AdvertiserZoneStatistics when end date is before start date
	 *
	 * @throws XmlRpcException
	 * @throws MalformedURLException
	 */
	public void testAdvertiserZoneStatisticsDateError() throws XmlRpcException,
			MalformedURLException {
		Object[] params = new Object[] { sessionId, advertiserId,
				DateUtils.MAX_DATE_VALUE, DateUtils.MIN_DATE_VALUE };

		try {
			client.execute(ADVERTISER_ZONE_STATISTICS_METHOD, params);
			fail(ErrorMessage.METHOD_EXECUTED_SUCCESSFULLY_BUT_SHOULD_NOT_HAVE);
		} catch (XmlRpcException e) {
			assertEquals(ErrorMessage.WRONG_ERROR_MESSAGE,
					ErrorMessage.START_DATE_IS_AFTER_END_DATE, e.getMessage());
		}
	}

	/**
	 * Test method with fields that has value of wrong type (error).
	 *
	 * @throws MalformedURLException
	 */
	public void testAdvertiserZoneStatisticsWrongTypeError()
			throws MalformedURLException {
		Object[] params = new Object[] { sessionId, advertiserId,
				TextUtils.NOT_DATE, DateUtils.MAX_DATE_VALUE };
		executeAdvertiserZoneStatisticsWithError(
				params,
				ErrorMessage
						.getMessage(
								ErrorMessage.INCORRECT_PARAMETERS_WANTED_DATE_GOT_STRING,
								"3"));

		params = new Object[] { sessionId, advertiserId,
				DateUtils.MIN_DATE_VALUE, TextUtils.NOT_DATE };
		executeAdvertiserZoneStatisticsWithError(
				params,
				ErrorMessage
						.getMessage(
								ErrorMessage.INCORRECT_PARAMETERS_WANTED_DATE_GOT_STRING,
								"4"));
	}
}
