/**
 */
package org.archstudio.prolog.xtext.prolog.impl;

import java.util.Collection;

import org.archstudio.prolog.xtext.prolog.Predicate;
import org.archstudio.prolog.xtext.prolog.PrologPackage;
import org.archstudio.prolog.xtext.prolog.SingleTerm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Predicate</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.archstudio.prolog.xtext.prolog.impl.PredicateImpl#getFunctor <em>Functor</em>}</li>
 *   <li>{@link org.archstudio.prolog.xtext.prolog.impl.PredicateImpl#getTerms <em>Terms</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PredicateImpl extends MinimalEObjectImpl.Container implements Predicate
{
  /**
   * The cached value of the '{@link #getFunctor() <em>Functor</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunctor()
   * @generated
   * @ordered
   */
  protected SingleTerm functor;

  /**
   * The cached value of the '{@link #getTerms() <em>Terms</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTerms()
   * @generated
   * @ordered
   */
  protected EList<SingleTerm> terms;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PredicateImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return PrologPackage.Literals.PREDICATE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SingleTerm getFunctor()
  {
    return functor;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFunctor(SingleTerm newFunctor, NotificationChain msgs)
  {
    SingleTerm oldFunctor = functor;
    functor = newFunctor;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrologPackage.PREDICATE__FUNCTOR, oldFunctor, newFunctor);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFunctor(SingleTerm newFunctor)
  {
    if (newFunctor != functor)
    {
      NotificationChain msgs = null;
      if (functor != null)
        msgs = ((InternalEObject)functor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrologPackage.PREDICATE__FUNCTOR, null, msgs);
      if (newFunctor != null)
        msgs = ((InternalEObject)newFunctor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrologPackage.PREDICATE__FUNCTOR, null, msgs);
      msgs = basicSetFunctor(newFunctor, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PrologPackage.PREDICATE__FUNCTOR, newFunctor, newFunctor));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<SingleTerm> getTerms()
  {
    if (terms == null)
    {
      terms = new EObjectContainmentEList<SingleTerm>(SingleTerm.class, this, PrologPackage.PREDICATE__TERMS);
    }
    return terms;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case PrologPackage.PREDICATE__FUNCTOR:
        return basicSetFunctor(null, msgs);
      case PrologPackage.PREDICATE__TERMS:
        return ((InternalEList<?>)getTerms()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case PrologPackage.PREDICATE__FUNCTOR:
        return getFunctor();
      case PrologPackage.PREDICATE__TERMS:
        return getTerms();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case PrologPackage.PREDICATE__FUNCTOR:
        setFunctor((SingleTerm)newValue);
        return;
      case PrologPackage.PREDICATE__TERMS:
        getTerms().clear();
        getTerms().addAll((Collection<? extends SingleTerm>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case PrologPackage.PREDICATE__FUNCTOR:
        setFunctor((SingleTerm)null);
        return;
      case PrologPackage.PREDICATE__TERMS:
        getTerms().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case PrologPackage.PREDICATE__FUNCTOR:
        return functor != null;
      case PrologPackage.PREDICATE__TERMS:
        return terms != null && !terms.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //PredicateImpl