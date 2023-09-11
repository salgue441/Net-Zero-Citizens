import Company from '../models/company.model'
import * as CompanyService from '../services/company.service'
import { NoRecord, Paginator, PaginationParams } from '../utils/RequestResponse'
import { RequestHandler } from 'express'

/**
 * @brief
 * Función del controlador que devuelve todos los proveedores
 * de la base de datos
 * @param req La request HTTP al servidor
 * @param res Un objeto paginador con los proveedores y la
 *            información de paginación
 */
export const getAllCompanies: RequestHandler<
  NoRecord,
  Paginator<Company>,
  NoRecord,
  PaginationParams<{ name?: string }>
> = async (req, res) => {
  const params = {
    start: req.query.start || 0,
    pageSize: req.query.pageSize || 10,
    filters: {
      name: req.query.name || '',
    },
  }

  const companies = await CompanyService.getAllCompanies(params)
  res.json({
    rows: companies.rows,
    start: params.start,
    pageSize: params.pageSize,
    total: companies.count,
  })
}

/**
 * @brief
 * Función del controlador que para registrar un nuevo proveedor
 * @param req La request HTTP al servidor
 * @param res Un objeto paginador con los proveedores y la
 *            información de paginación
 */
export const createCompany: RequestHandler<
  NoRecord,
  {companyId: string, message?: string, error?: string},
  {company: Company},
  NoRecord
  > = async (req, res) => {
    try {
      if(!req.body.company) res.status(400).json({ companyId: '', error: 'Missing company data'})
      const company = req.body.company
      const newCompany = await CompanyService.createCompany(company)    

      if(!newCompany) res.status(400).json({ companyId: '', error: 'Error creating company'})

      res.json({ companyId: newCompany?.dataValues.companyId, message: 'Company created'})
    } catch (error) {
      res.status(400).json({ companyId: '', error: 'Error creating company'})
    }
  }