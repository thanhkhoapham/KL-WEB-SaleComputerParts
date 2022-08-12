package server.service;

import java.util.List;

import server.dto.ReportDTO;
import server.repository.query_result.Report_QueryResult;


public interface ReportService {
	
	public ReportDTO createReport();
	
	public List<Report_QueryResult> getBestSeller();
}
