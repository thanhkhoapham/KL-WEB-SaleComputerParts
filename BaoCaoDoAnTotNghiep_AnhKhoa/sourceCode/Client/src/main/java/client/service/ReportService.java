package client.service;

import java.util.List;

import client.dto.ReportDTO;
import client.dto.Report_QueryResult;

public interface ReportService {
	
	public ReportDTO createReport(String token);
	public List<Report_QueryResult> getBestSeller(String token);
}
