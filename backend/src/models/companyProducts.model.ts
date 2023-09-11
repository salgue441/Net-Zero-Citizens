import {
  Table,
  Column,
  Model,
  DataType,
  BelongsTo,
  BelongsToMany,
  ForeignKey,
} from 'sequelize-typescript'
import Company from './company.model'
import Products from './products.model'

@Table({ tableName: 'COMPANY_PRODUCTS' })
export default class CompanyProducts extends Model {
  @Column({
    type: DataType.STRING,
    primaryKey: true,
    allowNull: false,
    field: 'COMPANY_PRODUCT_ID',
    unique: true,
  })
  companyProductId: string

  @ForeignKey(() => Products)
  @Column({
    type: DataType.STRING,
    allowNull: false,
    field: 'PRODUCT_ID',
  })
  productId: string

  @ForeignKey(() => Company)
  @Column({
    type: DataType.STRING,
    allowNull: false,
    field: 'COMPANY_ID',
  })
  companyId: string

  @Column({
    type: DataType.STRING,
    allowNull: false,
    field: 'PDF_PRODUCT_CERTIFICATION_URL',
    unique: true,
  })
  pdfProductCertificationUrl: string

  // @BelongsTo(() => Company) OTHER M TO N IMP
  // company: Company

  // @BelongsTo(() => Products) OTHER M TO N IMP
  // products: Products

  @BelongsToMany(() => Company, { through: () => CompanyProducts })
  companies!: Company[]
  @BelongsToMany(() => Products, { through: () => CompanyProducts })
  products!: Products[]
}