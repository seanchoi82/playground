package kr.ko.nexmain.server.MissingU.admin.dao;

import java.util.List;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.admin.model.ApkItem;
import kr.ko.nexmain.server.MissingU.admin.model.MsgDeleteMemberPack;
import kr.ko.nexmain.server.MissingU.missionmatch.model.MissionMatchJoinReqVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class AdminDaoImpl extends SqlMapClientDaoSupport implements AdminDao {
	public AdminDaoImpl(){
		super();
	}
	
	///-----------------------------------------------------------------------------------------
	// 쪽지함 재개발 (추후 구 버전 삭제)
	///-----------------------------------------------------------------------------------------
	
	@Override
	public List<Map<String, Object>> selectMsgList(Map<String, Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectMsgList", params);
	}

	@Override
	public Integer selectMsgListCnt(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectMsgListCnt", params);
	}
	
	@Override
	public List<Map<String, Object>> selectMsgConversation(Map<String, Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectMsgConversation", params);
	}

	@Override
	public Integer updateReadYn(long msgId) {
		return getSqlMapClientTemplate().update("Admin.updateMsgConversReadYn", msgId);
	}

	@Override
	public Integer deleteMsg(long msgId) {
		return getSqlMapClientTemplate().delete("Admin.deleteMsgConvers", msgId);
	}

	@Override
	public Integer deleteMsgGroup(MsgDeleteMemberPack pack) {
		return getSqlMapClientTemplate().delete("Admin.deleteMsgConversGroup", pack);
	}
	
	///-----------------------------------------------------------------------------------------
	// 쪽지함 재개발 (추후 구 버전 삭제)
	///-----------------------------------------------------------------------------------------
	
	@Autowired
	public AdminDaoImpl(SqlMapClient sqlMapClient) {
		super();
		setSqlMapClient(sqlMapClient);
	}
	
	public List<ApkItem> getApkItem() {
		return getSqlMapClientTemplate().queryForList("Admin.selectApkItem");
	}
	
	public void addApkItem(ApkItem apkItem) {
		getSqlMapClientTemplate().insert("Admin.insertApkItem", apkItem);
	}
	
	public void deleteApkItem(int apkId) {
		getSqlMapClientTemplate().delete("Admin.deleteApkItem", apkId);
	}
	
	/** 페이스매치 조회 */
	public List<Map<String,Object>> selectFmList(Map<String,Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectFmList", params);
	}
	
	/** 페이스매치 조회 Count */
	public Integer selectFmListCnt(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectFmListCnt", params);
	}
	
	/** 회원목록 조회 */
	public List<Map<String,Object>> selectMemberListNew(Map<String,Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectMemberListNew", params);
	}
	
	/** 회원목록 조회 Count */
	public Integer selectMemberListCntNew(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectMemberListCntNew", params);
	}
	
	/** 회원목록 조회 
	 * @deprecated*/
	public List<Map<String,Object>> selectMemberList(Map<String,Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectMemberList", params);
	}
	
	/** 회원목록 조회 Count 
	 * @deprecated*/
	public Integer selectMemberListCnt(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectMemberListCnt", params);
	}
	
	/** 톡투미 목록 조회 */
	public List<Map<String,Object>> selectTalkToMeList(Map<String,Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectTalkToMeList", params);
	}
	
	/** 톡투미 목록 조회 Count */
	public Integer selectTalkToMeListCnt(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectTalkToMeListCnt", params);
	}
	
	/** 톡투미 삭제 */
	public Integer deleteTalkToMe(Map<String,Object> params) {
		getSqlMapClientTemplate().delete("Admin.deleteTalkToMeReply", params);
		return getSqlMapClientTemplate().delete("Admin.deleteTalkToMe", params);
	}
	
	/** 톡투미 삭제 */
	public Integer deleteTalkToMeReply(Integer[] replyIds) {
		return getSqlMapClientTemplate().delete("Admin.deleteTalkToMeReplyByArray", replyIds);
	}
	
	/** 톡투미 삭제 */
	public Integer deleteTalkToMeArr(String[] talkIds) {
		getSqlMapClientTemplate().delete("Admin.deleteTalkToMeReplyByArrayTalkId", talkIds);
		return getSqlMapClientTemplate().delete("Admin.deleteTalkToMeByArrayTalkId", talkIds);
	}
	
	/** 결제내역 조회 */
	public List<Map<String,Object>> selectPayHistList(Map<String,Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectPayHistList", params);
	}
	
	/** 결제내역 조회 Count */
	public Integer selectPayHistListCnt(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectPayHistListCnt", params);
	}
	
	/** 포인트 사용내역 조회 */
	public List<Map<String,Object>> selectPointUseHistList(Map<String,Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectPointUseHistList", params);
	}
	 
	/** 포인트 사용내역 조회 Count */
	public Integer selectPointUseHistListCnt(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectPointUseHistListCnt", params);
	}
	
	/** 공지사항목록 조회 */
	public List<Map<String,Object>> selectNoticeList(Map<String,Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectNoticeList", params);
	}
	
	/** 공지사항목록 조회 Count */
	public Integer selectNoticeListCnt(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectNoticeListCnt", params);
	}
	
	/** 공지사항 조회 */
	public Map<String,Object> selectNotice(Map<String,Object> params) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Admin.selectNotice", params);
	}
	
	/** 공지사항 생성 */
	public Integer insertNotice(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Admin.insertNotice", params);
	}
	
	/** 공지사항 업데이트 */
	public int updateNotice(Map<String,Object> params) {
		return getSqlMapClientTemplate().update("Admin.updateNotice", params);
	}
	
	/** 공지사항 삭제 */
	public int deleteNotice(Map<String,Object> params) {
		return getSqlMapClientTemplate().update("Admin.deleteNotice", params);
	}
	
	/** 사용자가이드목록 조회 */
	public List<Map<String,Object>> selectGuideList(Map<String,Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectGuideList", params);
	}
	
	/** 사용자가이드목록 조회 Count */
	public Integer selectGuideListCnt(Map<String,Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectGuideListCnt", params);
	}
	
	/** 사용자가이드 조회 */
	public Map<String,Object> selectGuide(Map<String,Object> params) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Admin.selectGuide", params);
	}
	
	/** 사용자가이드 생성 */
	public void insertGuide(Map<String,Object> params) {
		getSqlMapClientTemplate().insert("Admin.insertGuide", params);
	}
	
	/** 사용자가이드 업데이트 */
	public int updateGuide(Map<String,Object> params) {
		return getSqlMapClientTemplate().update("Admin.updateGuide", params);
	}
	
	/** 사용자가이드 삭제 */
	public int deleteGuide(Map<String,Object> params) {
		return getSqlMapClientTemplate().update("Admin.deleteGuide", params);
	}
/*
	@Autowired
	private LobHandler lobHandler;

	private SimpleJdbcTemplate template;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static final String SELECT_ALL = "SELECT item_id, item_name, price, description FROM item";

	public List<ApkItem> findAll() {
		RowMapper<ApkItem> mapper = new BeanPropertyRowMapper<ApkItem>(ApkItem.class);
		return this.template.query(AdminDaoImpl.SELECT_ALL, mapper);
	}

	private static final String SELECT_BY_PRIMARY_KEY = "SELECT item_id, item_name, price, description FROM ApkItem WHERE item_id = ?";

	public ApkItem findByPrimaryKey(Integer itemId) {
		RowMapper<ApkItem> mapper = new BeanPropertyRowMapper<ApkItem>(ApkItem.class);
		return this.template.queryForObject(SELECT_BY_PRIMARY_KEY, mapper, itemId);
	}

	private static final String INSERT = "INSERT INTO item(item_name, price, description, picture) values (?, ?, ?, ?)";

	public void create(final ApkItem item) {
		this.jdbcTemplate.execute(INSERT, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
			@Override
			protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException,
					DataAccessException {
				int index = 0;
				ps.setString(++index, item.getItemName());
				ps.setInt(++index, item.getPrice().intValue());
				ps.setString(++index, item.getDescription());
				try {
					lobCreator.setBlobAsBytes(ps, ++index, item.getPicture().getBytes());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	public void delete(ApkItem item) {
		this.template.update("DELETE FROM ApkItem where item_id = ?", item.getItemId());
	}

	private static final String SELECT_BY_ITEM_NAME = "SELECT item_id, item_name, price, description FROM ApkItem WHERE item_name LIKE ?";

	public List<ApkItem> findByApkItemName(String itemName) {
		RowMapper<ApkItem> mapper = new BeanPropertyRowMapper<ApkItem>(ApkItem.class);
		return this.template.query(AdminDaoImpl.SELECT_BY_ITEM_NAME, mapper, itemName + "%");
	}

	private static final String UPDATE = "UPDATE ApkItem SET item_name = ?, price = ?, description = ?, picture = ? WHERE item_id = ?";

	public void udpate(final ApkItem item) {
		this.jdbcTemplate.execute(UPDATE, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
			@Override
			protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException,
					DataAccessException {
				int index = 0;
				ps.setString(++index, item.getItemName());
				ps.setInt(++index, item.getPrice().intValue());
				ps.setString(++index, item.getDescription());
				try {
					lobCreator.setBlobAsBytes(ps, ++index, item.getPicture().getBytes());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				ps.setInt(++index, item.getItemId().intValue());
			}
		});
	}

	public InputStream getPicture(Integer itemId) {
		return this.template.queryForObject("SELECT picture FROM ApkItem WHERE item_id = ?", new RowMapper<InputStream>() {
			public InputStream mapRow(ResultSet rs, int i) throws SQLException {
				return lobHandler.getBlobAsBinaryStream(rs, "picture");
			}
		}, itemId);
	}
	*/

	@Override
	public List<Map<String, Object>> selectMessageBoxList(Map<String, Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectMessageBoxList", params);
	}

	@Override
	public Integer selectMessageBoxListCnt(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectMessageBoxListCnt", params);
	}
	
	@Override
	public List<Map<String, Object>> getMsgBoxConversationByFriendsId(Map<String, Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectMsgBoxConversationByFriendsId", params);
	}
	
	@Override
	public List<Map<String, Object>> selectMessageBoxConversationFriends(Map<String, Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectMessageBoxConversationFriends", params);
	}

	@Override
	public Integer selectMessageBoxConversationFriendsCnt(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectMessageBoxConversationFriendsCnt", params);
	}
	
	/** 대화 삭제 */
	public Integer deleteMsgArr(String[] msgIds) {
		
		return getSqlMapClientTemplate().delete("Admin.deleteMsgByArrayMsgId", msgIds);
	}
	
	/** 1:1 문의 목록 */
	@Override
	public List<Map<String, Object>> selectManToManQuestionsList(Map<String, Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectManToManQuestionsList", params);
	}

	/** 1:1 문의 페이징용 전체 카운트 */
	@Override
	public Integer selectManToManQuestionsListCnt(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectManToManQuestionsListCnt", params);
	}
	
	/** 1:1 문의 삭제 */
	public Integer deleteManToManArr(String[] mIds) {
		return getSqlMapClientTemplate().delete("Admin.deleteManToManArrayByMId", mIds);
	}
	
	/** 1:1 문의 관리자 저장 */
	public Integer updateManToMan(Map<String, Object> params) {
		return getSqlMapClientTemplate().update("Admin.updateManToManByMId", params);
	}

	@Override
	public List<Map<String, Object>> selectMissionMatchList(Map<String, Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectMissionMatchList", params);
	}

	@Override
	public Integer selectMissionMatchListCnt(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectMissionMatchListCnt", params);
	}

	@Override
	public Map<String, Object> selectMissionMatch(Map<String, Object> params) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Admin.selectMissionMatch", params);
	}

	@Override
	public Integer insertMissionMatch(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().insert("Admin.insertMissionMatch", params);
	}

	@Override
	public int updateMissionMatch(Map<String, Object> params) {
		return getSqlMapClientTemplate().update("Admin.updateMissionMatch", params);
	}

	@Override
	public int deleteMissionMatch(Map<String, Object> params) {
		return getSqlMapClientTemplate().update("Admin.deleteMissionMatch", params);
	}
	
	@Override
	public List<Map<String, Object>> selectMissionMatchStatus(Map<String, Object> params) {
		if(params.containsKey("is_all") && params.get("is_all").toString().equals("1"))
			return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectMissionMatchStatusAll", params);
		else
			return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectMissionMatchStatusPeriod", params);
	}
	@Override
	public Integer selectMissionMatchStatusCnt(Map<String, Object> params) {
		if(params.containsKey("is_all") && params.get("is_all").toString().equals("1"))
			return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectMissionMatchStatusAllCnt", params);
		else
			return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectMissionMatchStatusPeriodCnt", params);
				
	}

	@Override
	public List<Map<String, Object>> selectJoinMissionMatchList(Map<String, Object> params) {
		return (List<Map<String, Object>>) getSqlMapClientTemplate().queryForList("Admin.selectJoinMissionMatchList", params);
	}

	@Override
	public Integer selectJoinMissionMatchListCnt(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Admin.selectJoinMissionMatchListCnt", params);
	}

	@Override
	public Map<String, Object> selectJoinMissionMatch(Map<String, Object> params) {
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("Admin.selectJoinMissionMatch", params);
	}

	@Override
	public Integer insertJoinMissionMatch(MissionMatchJoinReqVO inputVO) {
		return (Integer) getSqlMapClientTemplate().insert("Admin.insertJoinMissionMatch", inputVO);
	}

	@Override
	public int updateJoinMissionMatch(MissionMatchJoinReqVO inputVO) {
		return getSqlMapClientTemplate().update("Admin.updateJoinMissionMatch", inputVO);
	}

	@Override
	public int deleteJoinMissionMatch(String[] mJIds) {
		return getSqlMapClientTemplate().delete("Admin.deleteJoinMissionMatch", mJIds)
				+ getSqlMapClientTemplate().delete("Admin.deleteJoinMissionMatchVote", mJIds);
	}

	@Override
	public int voteJoinMissionMatch(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return 0;
	}
}