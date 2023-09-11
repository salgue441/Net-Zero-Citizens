import Company from '../models/company.model'
import { PaginationParams, PaginatedQuery } from '../utils/RequestResponse'

// TYPES
/**
 * @brief
 * Tipo de dato para el proveedor
 */
export type CompanyType = {
  companyId?: string,
  name: string,
  description: string,
  email: string,
  phoneNumber: string,
  webPage: string,
  street: string,
  streetNumber: number,
  city: string,
  state: string,
  zipCode: number,
  latitude: number,
  longitude: number,
  profilePicture?: string,
  pdfCurriculumUrl: string,
  pdfDicCdmxUrl?: string,
  pdfPeeFideUrl?: string,
  pdfGuaranteeSecurityUrl: string,
  status: string,
}

/**
 * @brief
 * Tipo de dato para el estatus de la compañia
 */
export type StatusEnum = 'approved' | 'pending_approval' | 'rejected'


/**
 * @brief
 * Función del servicio que devuelve todos los proveedores de la base de datos
 * @param params Los parametros de paginación
 * @returns Una promesa con los proveedores y la información de paginación
 */
export const getAllCompanies = async <T>(
  params: PaginationParams<T>
): Promise<PaginatedQuery<Company>> => {
  return await Company.findAndCountAll({
    limit: params.pageSize,
    offset: params.start,
  })
}

/**
 * @brief
 * Función del servicio para crear una nueva compañia
 * @param company La compañia a crear
 * @returns Una promesa con los proveedores y la información de paginación
 */
export const createCompany = async (company: CompanyType): Promise<Company | null> => {
  return await Company.create(company)
}
